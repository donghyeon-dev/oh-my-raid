package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.enums.SlugType;
import com.ohmyraid.common.result.CommonBadRequestException;
import com.ohmyraid.common.result.CommonInvalidInputException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.config.Constant;
import com.ohmyraid.domain.user.UserEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.wow_account.AccountSummaryDto;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_account.CharacterProfileSummary;
import com.ohmyraid.dto.wow_account.WowAccountDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClientWrapper;
import com.ohmyraid.mapper.CharacterMapper;
import com.ohmyraid.repository.user.UserRepository;
import com.ohmyraid.repository.character.CharacterRepository;
import com.ohmyraid.repository.raid.RaidDetailRepository;
import com.ohmyraid.repository.raid.RaidEncounterRepository;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;

    private final RaidEncounterRepository raidEncounterRepository;

    private final RaidDetailRepository raidDetailRepository;

    private final WowClientWrapper wowClientWrapper;
    private final RedisUtils redisUtils;

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
        UserEntity userEntity = userRepository.findByUserId(accountId);

        // 계정 내 캐릭터정보 Feign 호출 및 파싱
        AccountSummaryDto accountProfileSummary = wowClientWrapper.fetchAccountProfileSummary(
                WowClientRequestDto.builder()
                        .namespace(Constant.Auth.NAMESPACE)
                        .locale(Constant.Auth.LOCALE)
                        .accessToken(bzToken)
                        .region(Constant.Auth.REGION)
                        .build());


        List<CharacterDto> characterList = parseCharacterList(accountProfileSummary.getWowAccounts(), accountId);

        for (CharacterDto characterDto : characterList) {
            CharacterProfileSummary specDto = CharacterProfileSummary.builder().build();
            try {
                specDto = wowClientWrapper.fetchCharacterProfileSummary(
                        WowClientRequestDto.builder()
                                .namespace(Constant.Auth.NAMESPACE)
                                .accessToken(bzToken)
                                .locale(Constant.Auth.LOCALE)
                                .slugEnglishName(SlugType.getSlugEnglishNameByKorName(characterDto.getSlug()))
                                .characterName(characterDto.getName())
                                .build());
            }catch (Exception e){
                e.printStackTrace();
                log.error("Error occurred while wowClient.getCharacterSpec() with character name of {}", characterDto.getName());
                continue;
            }


            // 정보 합치기
            CharacterDto targetCharacter = generateCharacterEntityByCharacterProfileSummary(specDto);

            // insert를 하기 전 DB에 데이터 검증
            CharacterEntity existCharacterEntity = characterRepository.findCharacterByCharacterSeNumber(characterDto.getCharacterSeNumber());
            Long existCharacterId = null;
            boolean isExist = false;
            if ( !ObjectUtils.isEmpty(existCharacterEntity)) {
                targetCharacter.setCharacterId(existCharacterEntity.getCharacterId());
                targetCharacter.setUserId(existCharacterEntity.getUserEntity().getUserId());
            }

            CharacterEntity target = CharacterMapper.INSTANCE.characterDtoToEntity(targetCharacter);
            characterRepository.save(target);
            // Battle.Net API의 특성상 텀을 두고 Request를 보내야한다.
//            Thread.sleep(2500L);
        }

        return true;
    }

    /**
     * 캐릭터 프로필 요약정보를 CharacterEntity에 맞게 파싱하여 Entity 생성
     * @param profileSummary
     */
    private CharacterDto generateCharacterEntityByCharacterProfileSummary(CharacterProfileSummary profileSummary) {
        if(ObjectUtils.isEmpty(profileSummary)){
           log.warn("There is no CharacterProfileSummary");
           throw new CommonInvalidInputException();
        };

        return CharacterDto.builder()
                .characterSeNumber(profileSummary.getId())
                .name(profileSummary.getName())
                .level(profileSummary.getLevel())
                .playableClass(profileSummary.getCharacterClass().getName())
                .specialization(profileSummary.getActiveSpec().getName())
                .race(profileSummary.getRace().getName())
                .gender(profileSummary.getGender().getName())
                .faction(profileSummary.getFaction().getName())
                .equippedItemLevel(profileSummary.getEquippedItemLevel())
                .averageItemLvel(profileSummary.getAverageItemLevel())
                .slug(profileSummary.getRealm().getName())
                .lastCrawledAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
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
                characterRepository.findAllByUserEntity_UserIdOrderByEquippedItemLevelDesc(accountId);


        List<CharacterDto> resultList = CharacterMapper.INSTANCE.characterEntitiesToDtoList(myCharacters);

        return resultList;

    }

    public Boolean getCharactersRaidDetailByAccount(long accountId) throws Exception {

        // 1. Redis에서 토큰 가져오기
        String bzToken = redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class);
        if (ObjectUtils.isEmpty(bzToken)) {
            throw new CommonServiceException(ErrorResult.NO_BZ_TOKEN);
        }
        // 2. 계정의 모든 캐릭터 정보 가져오기
        List<CharacterDto> targetCharacterDtoList = characterRepository.findCharacterDtosByUserId(accountId);

        // 3. BlizzardAPI에서 계정의 모든 캐릭터 정보의 레이드 정보를 가져옴
        List<RaidInfoDto> characterRaidInfoList = targetCharacterDtoList.stream()
                .flatMap(characterDto -> {
                    try {
                        RaidInfoDto result = wowClientWrapper.fetchRaidEncounter(
                                WowClientRequestDto.builder()
                                        .namespace(Constant.Auth.NAMESPACE)
                                        .accessToken(bzToken)
                                        .locale(Constant.Auth.LOCALE)
                                        .slugEnglishName(SlugType.getTypeByName(characterDto.getSlug()).getSlugEnglishName())
                                        .characterName(characterDto.getName())
                                        .accountId(accountId)
                                        .characterId(characterDto.getCharacterId())
                                        .build());

                        result.setCharacterId(characterDto.getCharacterId());
                        return Stream.of(result);    // On success, return a Stream containing the result.
                    } catch (Exception e) {
                        // Log the exception, if necessary.
                        return Stream.empty();    // On exception, return an empty Stream.
                    }
                }).collect(Collectors.toList());
        // 4. 레이드 정보를 파싱
        List<RaidDetailDto> raidDetailDtoList = parseCharacterRaidInfosToRaidDetails(characterRaidInfoList);

        // 5. DB에 해당하는 레이드정보가 있다면 update, 없다면 insert
        if (!ObjectUtils.isEmpty(raidDetailDtoList)) {

            insertRaidDetail(raidDetailDtoList);
        }

        return true;
    }

    public void insertRaidDetail(List<RaidDetailDto> raidDetailDtoList) {
        raidDetailDtoList.stream()
                .filter(raidDetailDto -> raidDetailDto.getDifficulty() != null && !ObjectUtils.isEmpty(raidDetailDto.getDifficulty()))
                .forEach(raidDetailDto -> {
                    Long detailId = null;
                    if(raidDetailDto.getCharacterId() != null){
                        detailId = raidDetailRepository.findRaidDetailIdByDto(raidDetailDto);
                    }
                    raidDetailRepository.save(RaidDetailEntity.builder()
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
                            .build());
                });
    }

    public List<RaidDetailDto> parseCharacterRaidInfosToRaidDetails(List<RaidInfoDto> characterRaidInfoList) {

        List<RaidDetailDto> raidDetailDtoList = new ArrayList<>();
        for (RaidInfoDto charactersRaidInfo : characterRaidInfoList) {

            if (ObjectUtils.isEmpty(charactersRaidInfo.getExpansions())) {
                log.error("This character({}) has no raid info entire expansions.", charactersRaidInfo.getCharacterId());
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
                            int currentBossKills = instanceBoss.getCompletedCount(); // 1
                            LocalDateTime lastKilledAt = DatetimeUtils.unixToLocalDateTime(Long.parseLong(instanceBoss.getLastKillTimestamp())); // 2023.01.01 12:31:33
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
        return raidDetailDtoList;
    }


    /**
     * 특정 캐릭터의 레이드 세부 정보를 조회합니다.
     *
     * @param characterRaidInfoRequest 세부 정보를 조회하기 위한 캐릭터 레이드 정보 요청 객체. 이 객체는 캐릭터 ID를 포함해야 합니다.
     * @return CharacterRaidInfoDto 내용이 포함된 목록. 이 목록은 레이드 세부 정보를 포함합니다.
     * @throws CommonInvalidInputException 캐릭터 ID가 없는 경우 발생하는 예외입니다.
     */
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
     * @param userId     생성된 'CharacterDto'에 설정할 계정 ID입니다.
     * @return {@code WowAccountDto}를 {@code CharacterDto}로 변환한 목록입니다.
     * 각각의 'CharacterDto'는 원래 'WowAccountDto'의 캐릭터 정보와 주어진 계정 ID를 포함합니다.
     */
    List<CharacterDto> parseCharacterList(List<WowAccountDto> wowAccountDto, long userId) {
        List<CharacterDto> requestList = new ArrayList<>();
            requestList = wowAccountDto.get(0).getCharacters()
                    .stream()
                    .filter(c -> c.getLevel() >= 60)
                    .map( c ->
                            CharacterDto.builder()
                                    .characterSeNumber(c.getId())
                                    .userId(userId)
                                    .name(c.getName().toLowerCase())
                                    .level(c.getLevel())
                                    .playableClass(c.getPlayableClass().getName())
                                    .race(c.getPlayableRace().getName())
                                    .faction(c.getFaction().getName())
                                    .gender(c.getGender().getName())
                            .build()
                            ).collect(Collectors.toList());

        return requestList;
    }

    /**
     * 캐릭터 의 요약정보를 반환한다.
     * DB에 없다면 API를 통해 가져와서 DB에 저장하고 반환한다.
     * @param characterSpecRequest
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public CharacterDto getCharacterProfile(CharacterSpecRequest characterSpecRequest) throws JsonProcessingException {
        if( !StringUtils.hasText(characterSpecRequest.getCharacterName()) &&
                !StringUtils.hasText(characterSpecRequest.getSlugName())){
            throw new CommonBadRequestException();
        }

        // DB조회
        CharacterDto characterDto = characterRepository.findCharacterDtoBySlugAndName(characterSpecRequest);
        if(ObjectUtils.isEmpty(characterDto)){
            log.info("There is no chracter in DB. CharacterName={}, slugName={}", characterSpecRequest.getCharacterName(), characterSpecRequest.getSlugName());
            // fetch CharacterProfile
            CharacterProfileSummary characterProfileSummary = wowClientWrapper.fetchCharacterProfileSummary(
                    WowClientRequestDto.builder()
                            .namespace(Constant.Auth.NAMESPACE)
                            .accessToken(redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class))
                            .locale(Constant.Auth.LOCALE)
                            .slugEnglishName(SlugType.getSlugEnglishNameByKorName(characterSpecRequest.getSlugName()))
                            .characterName(characterSpecRequest.getCharacterName())
                            .build());
            log.info("Fetched CharacterSpec={}", characterProfileSummary);

            characterDto = generateCharacterEntityByCharacterProfileSummary(characterProfileSummary);
            CharacterEntity savedEntity = characterRepository.save(CharacterMapper.INSTANCE.characterDtoToEntity(characterDto));

            // fetch RaidInfo
            RaidInfoDto raidInfoDto = wowClientWrapper.fetchRaidEncounter(
                    WowClientRequestDto.builder()
                            .namespace(Constant.Auth.NAMESPACE)
                            .accessToken(redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class))
                            .locale(Constant.Auth.LOCALE)
                            .slugEnglishName(SlugType.getSlugEnglishNameByKorName(characterSpecRequest.getSlugName()))
                            .characterName(characterSpecRequest.getCharacterName())
                            .build());

            raidInfoDto.setCharacterId(savedEntity.getCharacterId());
            List<RaidDetailDto> raidDetailList =  parseCharacterRaidInfosToRaidDetails(Stream.of(raidInfoDto).collect(Collectors.toList()));
            if (!ObjectUtils.isEmpty(raidDetailList)) {

                insertRaidDetail(raidDetailList);
            }

        }



        return characterDto;
    }
}
