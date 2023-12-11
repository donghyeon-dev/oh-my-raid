package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ohmyraid.common.enums.SlugType;
import com.ohmyraid.common.result.CommonInvalidInputException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.config.Constant;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_account.CharacterSpecInfoDto;
import com.ohmyraid.dto.wow_account.WowAccountDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClientWrapper;
import com.ohmyraid.mapper.CharacterMapper;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.raid.RaidDetailRepository;
import com.ohmyraid.repository.raid.RaidEncounterRepository;
import com.ohmyraid.utils.ConvertUtils;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRespository characterRespository;
    private final AccountRepository accountRepository;

    private final RaidEncounterRepository raidEncounterRepository;

    private final RaidDetailRepository raidDetailRepository;

    private final WowClientWrapper wowClientWrapper;
    private final RedisUtils redisUtils;
    private final ObjectMapper mapper;
    private final ConvertUtils convertUtils;
    private final JPAQueryFactory queryFactory;

    /**
     * 최초 혹은 추가 캐릭터정보를 가져올때 사용
     * Login한 배틀넷계정의 캐릭터 종합 정보를 블리자드 API를 통해 가져와서, 각 캐릭터의 기본정보를 DB에 저장한다.
     *
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public Boolean getChractersByAccount(long accountId) throws JsonProcessingException, InterruptedException {
        // 토큰가져오기 및 사용될 변수들 생성
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String bzToken = redisUtils.getRedisValue(token, UserSessionDto.class).getBlizzardAccessToken();
        if (ObjectUtils.isEmpty(bzToken)) {
            log.error("캐릭터 정보 추가를 위해서는 AuthorizationCode로 받아온 AccessToken이 필요합니다.");
            throw new CommonServiceException(ErrorResult.NO_BZ_TOKEN);
        }
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);

        // 계정 내 캐릭터정보 Feign 호출 및 파싱
        Map<String, Object> accountProfileSummaryMap = wowClientWrapper.getAccountProfileSummary(Constant.Auth.NAMESPACE
                , Constant.Auth.LOCALE
                , bzToken
                , Constant.Auth.REGION);
        List<WowAccountDto> wowAccountDto = mapper.convertValue(accountProfileSummaryMap.get("wow_accounts"),
                TypeFactory.defaultInstance().constructCollectionType(List.class, WowAccountDto.class));

        List<CharacterDto> characterList = parseCharacterList(wowAccountDto, accountId);

        for (CharacterDto characterDto : characterList) {
            CharacterSpecInfoDto specDto = CharacterSpecInfoDto.builder().build();
            try {
                specDto = wowClientWrapper.getCharacterSpec(Constant.Auth.NAMESPACE
                        , bzToken
                        , Constant.Auth.LOCALE
                        , characterDto.getRealm()
                        , characterDto.getName());
            }catch (Exception e){
                e.printStackTrace();
                log.error("Error occurred while wowClient.getCharacterSpec() with character name of {}", characterDto.getName());
                continue;
            }

            // insert를 하기 전 DB에 데이터 검증
            CharacterEntity existCharacterEntity = characterRespository.findCharacterByCharacterSeNumber(characterDto.getCharacterSeNumber());
            Long existCharacterId = null;
            boolean isExist = false;

            if (!ObjectUtils.isEmpty(existCharacterEntity)) {
                existCharacterId = existCharacterEntity.getCharacterId();
                isExist = true;
            }
            // 정보 합치기
            if (!ObjectUtils.isEmpty(specDto.getCovenant_progress())) {
                characterDto.setExpansionOption(specDto.getCovenant_progress().getChosenCovenant().getName());
                characterDto.setExpansionOptionLevel(specDto.getCovenant_progress().getRenownLevel());
            }
            ;
            CharacterEntity characterEntity = CharacterEntity.builder()
                    .accountEntity(accountEntity)
                    .characterId(isExist ? existCharacterId : null)
                    .characterSeNumber(characterDto.getCharacterSeNumber())
                    .name(characterDto.getName())
                    .level(characterDto.getLevel())
                    .playableClass(characterDto.getPlayableClass())
                    .specialization(specDto.getActive_spec().getName())
                    .race(characterDto.getRace())
                    .gender(characterDto.getGender())
                    .faction(characterDto.getFaction())
                    .equippedItemLevel(specDto.getEquipped_item_level())
                    .averageItemLvel(specDto.getAverage_item_level())
                    .slug(characterDto.getSlug())
                    .lastCrawledAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .expansionOption(characterDto.getExpansionOption())
                    .expansionOptionLevel(characterDto.getExpansionOptionLevel())
                    .build();

            characterRespository.save(characterEntity);
            // Battle.Net API의 특성상 텀을 두고 Request를 보내야한다.
//            Thread.sleep(2500L);
        }

        return true;
    }

    /**
     * 계정의 모든 캐릭터 정보를 가져온다.
     *
     * @return
     * @throws JsonProcessingException
     */
    public List<CharacterDto> getCharactersOfAccount(long accountId) throws JsonProcessingException {
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        List<CharacterEntity> myCharacters =
                characterRespository.findAllByAccountEntity_AccountIdOrderByEquippedItemLevelDesc(accountId);


        List<CharacterDto> resultList = CharacterMapper.INSTANCE.characterEntitiesToDtoList(myCharacters);

        return resultList;

    }

    public Boolean getRaidDetail(long accountId) throws Exception {

        String bzToken = redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class);
        if (ObjectUtils.isEmpty(bzToken)) {
            throw new CommonServiceException(ErrorResult.NO_BZ_TOKEN);
        }
        List<CharacterDto> targetCharacterDtoList = CharacterMapper.INSTANCE.characterEntitiesToDtoList(
                characterRespository.findAllByAccountEntity_AccountIdOrderByEquippedItemLevelDesc(accountId)
        );


        // Todo 비동기로 구현
        // 1. 캐릭터정보를 비동기(FeignAsync)를 통해 가져옴 (suuplyAsync)
        // 1-1. Exception발생시 해당 캐릭터를 조회하는 정보?를 Kafka Queue에 넣음
        // 1-1-2. Kafka Consumer 폴링
        // 1-1-3. 만약에 폴링을 했을때 토픽에 데이터가 있으면 해당 데이터를 가져와서 다시 캐릭터정보를 가져옴
        // 2. 가져온 데이터들을 파싱하여 각각 save? or saveAll? (thenAcceptAsync)

        //

        List<RaidInfoDto> characterRaidInfoList = targetCharacterDtoList.stream()
                .map(
                        c -> {
                            RaidInfoDto result =
                                    wowClientWrapper.getRaidEncounter(
                                            WowClientRequestDto.builder()
                                                    .namespace(Constant.Auth.NAMESPACE)
                                                    .accessToken(bzToken)
                                                    .locale(Constant.Auth.LOCALE)
                                                    .slugEnglishName(SlugType.getTypeByName(c.getSlug()).getSlugEnglishName())
                                                    .characterName(c.getName())
                                                    .build());

                            result.setCharacterId(c.getCharacterId());
                            return result;
                        }
                ).collect(Collectors.toList());
        List<RaidDetailDto> raidDetailDtoList = new ArrayList<>();
        for (RaidInfoDto charactersRaidInfo : characterRaidInfoList) {

            if (ObjectUtils.isEmpty(charactersRaidInfo.getExpansions())) {
                log.error("This character has no raid info entire expansions");
                continue;
            }
            List<Expansions> expansionRaidInfoList = charactersRaidInfo.getExpansions().stream()
                    .filter(expansions -> !ObjectUtils.isEmpty(expansions)
                            && expansions.getExpansion().getId() >= 395 // 395:군단 => 군단이후
                            && !expansions.getExpansion().getName().equals(Constant.CURRENT_SEASON))
                    .collect(Collectors.toList());

            for (Expansions expansionsRaidInfo : expansionRaidInfoList) {
                String expansionName = expansionsRaidInfo.getExpansion().getName(); // LEGION
                Long expansionId = expansionsRaidInfo.getExpansion().getId();
                List<Instances> expansionInstanceInfoList = expansionsRaidInfo.getInstances();
                for (Instances raidInstances : expansionInstanceInfoList) {
                    String instanceName = raidInstances.getInstance().getName(); // TOMB_OF_SARGERAS
                    Long instanceId = raidInstances.getInstance().getId();
                    List<ModesDto> instanceModeList = raidInstances.getModes();
                    for (ModesDto instanceMode : instanceModeList) {
                        String instanceDifficulty = instanceMode.getDifficulty().getType();// MYTHIC
                        List<Encounters> instanceBossList = instanceMode.getProgress().getEncounters();
                        for (Encounters instanceBoss : instanceBossList) {
                            String bossName = instanceBoss.getEncounter().getName(); // Kiljeden
                            long bossId = instanceBoss.getEncounter().getId();
                            int currentBossKills = instanceBoss.getCompleted_count(); // 1
                            LocalDateTime lastKilledAt = DatetimeUtils.unixToLocalDateTime(Long.parseLong(instanceBoss.getLast_kill_timestamp())); // 2023.01.01 12:31:33
                            RaidDetailDto raidDetailDto = RaidDetailDto.builder()
                                    .characterId(charactersRaidInfo.getCharacterId())
                                    .expansionName(expansionName)
                                    .expansionId(expansionId)
                                    .difficulty(instanceDifficulty)
                                    .instanceName(instanceName)
                                    .instanceId(instanceId)
                                    .bossName(bossName)
                                    .bossId(bossId)
                                    .completedCount(currentBossKills)
                                    .lastCrawledAt(DatetimeUtils.now())
                                    .lastKilledAt(lastKilledAt)
                                    .build();
                            raidDetailDtoList.add(raidDetailDto);
                        }
                    }
                }
            }
        }

        List<RaidDetailEntity> raidDetailEntityList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(raidDetailDtoList)) {
            raidDetailEntityList = raidDetailDtoList.stream()
                    .filter(raidDetailDto -> raidDetailDto.getDifficulty() != null && !ObjectUtils.isEmpty(raidDetailDto.getDifficulty()))
                    .map(raidDetailDto -> {
                        Long detailId = raidDetailRepository.findRaidDetailIdByDto(raidDetailDto);

                        return RaidDetailEntity.builder()
                                .detailId(detailId)
                                .characterEntity(CharacterEntity.builder().characterId(raidDetailDto.getCharacterId()).build())
                                .expansionName(raidDetailDto.getExpansionName())
                                .expansionId(raidDetailDto.getExpansionId())
                                .difficulty(raidDetailDto.getDifficulty())
                                .instanceName(raidDetailDto.getInstanceName())
                                .instanceId(raidDetailDto.getInstanceId())
                                .bossName(raidDetailDto.getBossName())
                                .bossId(raidDetailDto.getBossId())
                                .completedCount(raidDetailDto.getCompletedCount())
                                .lastCrawledAt(DatetimeUtils.now())
                                .lastKilledAt(raidDetailDto.getLastKilledAt())
                                .build();
                    })
                    .collect(Collectors.toList());
        }
        raidDetailRepository.saveAll(raidDetailEntityList);

        return true;
    }


    public List<CharacterRaidInfoDto> getSpecificCharacterRaidDetailInfo(CharacterRaidInfoRequest characterRaidInfoRequest) {
        if(ObjectUtils.isEmpty(characterRaidInfoRequest.getCharacterId())){
            throw new CommonInvalidInputException();
        }
        return raidDetailRepository.findRaidDetailListBySearchParam(characterRaidInfoRequest);
    }

    ;

    /**
     * 주어진 {@code WowAccountDto} 목록과 계정 ID를 사용하여 {@code CharacterDto}의 목록을 생성합니다.
     *
     * @param wowAccountDto 변환할 'WowAccountDto' 목록입니다.
     * @param accountId     생성된 'CharacterDto'에 설정할 계정 ID입니다.
     * @return {@code WowAccountDto}를 {@code CharacterDto}로 변환한 목록입니다.
     * 각각의 'CharacterDto'는 원래 'WowAccountDto'의 캐릭터 정보와 주어진 계정 ID를 포함합니다.
     */
    List<CharacterDto> parseCharacterList(List<WowAccountDto> wowAccountDto, long accountId) {
        List<CharacterDto> requestList = new ArrayList<>();
            requestList = wowAccountDto.get(0).getCharacters()
                    .stream()
                    .filter(c -> c.getLevel() > 60)
                    .map( c ->
                            CharacterDto.builder()
                                            .characterSeNumber(c.getId())
                                            .accountId(accountId)
                                            .name(c.getName().toLowerCase())
                                            .level(c.getLevel())
                                            .playableClass(c.getPlayableClass().getName())
                                            .race(c.getPlayableRace().getName())
                                            .slug(c.getRealm().getName())
                                            .realm(c.getRealm().getSlug())
                                            .faction(c.getFaction().getName())
                                            .gender(c.getGender().getName())
                                    .build()
                            ).collect(Collectors.toList());

        return requestList;
    }

}
