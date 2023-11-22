package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ohmyraid.common.enums.SlugType;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.config.Constant;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_account.CharacterSpecInfoDto;
import com.ohmyraid.dto.wow_account.WowAccountDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClient;
import com.ohmyraid.mapper.CharacterMapper;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.raid.RaidEncounterRepository;
import com.ohmyraid.utils.ConvertUtils;
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
    private final WowClient wowClient;
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
        Map<String, Object> accountProfileSummaryMap = wowClient.getAccountProfileSummary(Constant.Auth.NAMESPACE
                , Constant.Auth.LOCALE
                , bzToken
                , Constant.Auth.REGION);
        List<WowAccountDto> wowAccountDto = mapper.convertValue(accountProfileSummaryMap.get("wow_accounts"),
                TypeFactory.defaultInstance().constructCollectionType(List.class, WowAccountDto.class));
        List<CharacterDto> characterList = parseCharacterList(wowAccountDto, accountId);

        for (CharacterDto characterDto : characterList) {
            CharacterSpecInfoDto specDto = CharacterSpecInfoDto.builder().build();
            try {
                specDto = wowClient.getCharacterSpec(Constant.Auth.NAMESPACE
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

    /**
     * 블리자드 API를 통해
     * 캐릭터의 레이드 정보를 가져온다.
     *
     * @return
     */
    public Boolean getRaidEncounter(long accountId) throws Exception {
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String bzToken = redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class);
        if (ObjectUtils.isEmpty(bzToken)) {
            throw new CommonServiceException(ErrorResult.NO_BZ_TOKEN);
        }
        List<CharacterEntity> charactersEntities =
                characterRespository.findAllByAccountEntity_AccountIdOrderByEquippedItemLevelDesc(accountId);
        List<CharacterDto> characterDtos = CharacterMapper.INSTANCE.characterEntitiesToDtoList(charactersEntities);


        List<RaidEncounterDto> encounterDtoList = accountRaidInfoToRaidEncounter(characterDtos, bzToken);

        List<RaidEncounterEntity> raidEncounterEntities = new ArrayList<>();
        if (!ObjectUtils.isEmpty(encounterDtoList)) {
            raidEncounterEntities = encounterDtoList.stream()
                    .filter(a -> a.getDifficulty() != null && !ObjectUtils.isEmpty(a.getDifficulty()))
                    .map(a -> {
                        RaidEncounterDto encounterDto = new RaidEncounterDto();
                        encounterDto.setCharacterId(a.getCharacterId());
                        encounterDto.setDifficulty(a.getDifficulty());
                        encounterDto.setInstanceName(a.getInstanceName());
                        encounterDto.setExpansionName(a.getExpansionName());
                        encounterDto = raidEncounterRepository.findRaidEncounterEntityByDto(encounterDto);

                        long encounterId = !ObjectUtils.isEmpty(encounterDto) ? encounterDto.getEncounterId() : 0L;

                        RaidEncounterEntity entity = RaidEncounterEntity.builder()
                                .characterEntity(characterRespository.findCharacterEntityByCharacterId(a.getCharacterId()))
                                .encounterId(encounterId)
                                .difficulty(a.getDifficulty())
                                .expansionName(a.getExpansionName())
                                .instanceName(a.getInstanceName())
                                .lastCrawledAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                                .progress(a.getProgress())
                                .build();
                        return entity;
                    })
                    .collect(Collectors.toList());
            log.debug("raidEuncounterEntities'is {}", raidEncounterEntities);
        }
        raidEncounterRepository.saveAll(raidEncounterEntities);

        return true;
    }

    /**
     * 특정 캐릭터의 레이드 정보를 가져온다.
     *
     * @param characterId
     * @param accountId
     * @return
     * @throws Exception
     */
    public List<CharacterRaidInfoDto> getSpecificCharacterRaidInfo(long characterId, long accountId) throws Exception {

        return raidEncounterRepository.findCharacterRaidInfoByCharacterId(characterId, accountId);
    }


    public List<CharacterRaidInfoDto> getSpecificCharacterEncounterInfo(long characterId) {

        return null;
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

    /**
     * 실제 캐릭터 정보와 블리자드 API 토큰을 사용하여 RaidEncounter Dto 목록을 생성
     *
     * @param characterDtos 실제 캐릭터 DTO 정보의 목록
     * @param bzToken             블리자드 API 주소를 호출할 때 사용되는 토큰
     * @return 생성된 RaidEncounterDto 목록을 반환합니다. 이 목록은 캐릭터 ID,
     * 확장팩 이름, 던전 이름, 난이도 등 정보를 기반으로 각각을 표현하는 RaidEncounterDto 오브젝트를 포함합니다.
     * 각 RaidEncounterDto 객체는 하나의 공격대와 그 상세 정보를 나타냅니다.
     */
    List<RaidEncounterDto> accountRaidInfoToRaidEncounter(List<CharacterDto> characterDtos, String bzToken) {
        List<RaidEncounterDto> encounterDtoList = new ArrayList<>();

        // 캐릭터 정보들 Stream을 돌려 RaidEncounter List 생성
        List<RaidInfDto> accountRaidInfList = characterDtos.stream()
                .map(
                        c -> {
                            RaidInfDto result =
                                    wowClient.getRaidEncounter(Constant.Auth.NAMESPACE
                                            , bzToken
                                            , Constant.Auth.LOCALE
                                            , SlugType.getEnglishNameBySlugname(c.getSlug()).getSlugEnglishName(),
                                            c.getName());
//                            try {
//                                // BlizzardAPI의 빠른 호출 방지를 위한...
//                                Thread.sleep(3000L);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            result.setCharacterId(c.getCharacterId());
                            return result;
                        }
                ).collect(Collectors.toList());
        // 필터링
        log.debug("accountRaidInfList is {}", accountRaidInfList);
        accountRaidInfList = accountRaidInfList.stream()
                .filter(raidInfList -> raidInfList.getExpansions() != null && !ObjectUtils.isEmpty(raidInfList))
                .collect(Collectors.toList());

        // stream으론 답이 안나온다.. 중첩된 For문을 사용하는 수밖에...

        for (RaidInfDto raidInfDto : accountRaidInfList) {

            long characterId = raidInfDto.getCharacterId();


            List<ExpansionsDto> expansionsList = raidInfDto.getExpansions();
            expansionsList = expansionsList.stream()
                    .filter(expansionDto -> !ObjectUtils.isEmpty(expansionDto))
                    .collect(Collectors.toList());

            if (!ObjectUtils.isEmpty(expansionsList)) {
                for (ExpansionsDto expansionDto : expansionsList) {
                    String expansionName = expansionDto.getExpansion().getName();


                    List<InstancesDto> instancesList = expansionDto.getInstances();
                    for (InstancesDto instancesDto : instancesList) {
                        String instanceName = instancesDto.getInstance().getName();


                        List<ModesDto> difficultyList = instancesDto.getModes();
                        for (ModesDto modeDto : difficultyList) {
                            RaidEncounterDto raidEncounterDto = new RaidEncounterDto();
                            raidEncounterDto.setCharacterId(characterId);
                            raidEncounterDto.setExpansionName(expansionName);
                            raidEncounterDto.setInstanceName(instanceName);

                            raidEncounterDto.setDifficulty(modeDto.getDifficulty().getType());
                            raidEncounterDto.setProgress(convertUtils.progressToString(modeDto.getProgress()));
                            encounterDtoList.add(raidEncounterDto);
                        }

                    }
                }
            }

        }
        return encounterDtoList;
    }
}
