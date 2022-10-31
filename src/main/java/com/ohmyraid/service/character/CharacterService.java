package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import com.ohmyraid.dto.wow_account.SpecInfDto;
import com.ohmyraid.dto.wow_account.WowAccountDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClient;
import com.ohmyraid.mapper.CharacterMapperImpl;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.raid.RaidEncounterRepository;
import com.ohmyraid.utils.ConvertUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${bz.namespace}")
    private final String namespace = null;

    @Value("${bz.locale}")
    private final String locale = null;

    @Value("${bz.region}")
    private final String region = null;

    /**
     * 블리자드 API를 통해
     * 계정의 캐릭터 종합 정보를 가져와서, 각 캐릭터의 기본정보를 DB에 저장한다.
     *
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public Boolean getTotalSummary() throws JsonProcessingException, InterruptedException {
        // 토큰가져오기 및 사용될 변수들 생성
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String bzToken = redisUtils.getSession(token).getBzAccessToken();
        long accountId = redisUtils.getSession(token).getAccountId();
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);

        // AccountSummary Feign 호출 및 파싱
        Map<String, Object> resultSummary = wowClient.getAccountProfileSummary(namespace, locale, bzToken, region);
        List<WowAccountDto> wowAccountDto = mapper.convertValue(resultSummary.get("wow_accounts")
                , TypeFactory.defaultInstance().constructCollectionType(List.class, WowAccountDto.class));
        log.debug("wowAccountDto is {}", wowAccountDto);
        int size = wowAccountDto.size();

        // SpecInf 호출을 하기 전 데이터 재파싱
        List<ActualCharacterDto> requestList = new ArrayList<>();
        if (size == 1) {
            // 리스트가 하나니까 걍 꺼내
            requestList = wowAccountDto.get(0).getCharacters()
                    .stream()
                    .filter(c -> c.getLevel() > 50)
                    .map(
                            c -> {
                                ActualCharacterDto characterDto = new ActualCharacterDto();
                                characterDto.setCharacterSeNumber(c.getId());
                                characterDto.setAccountId(accountEntity.getAccountId());
                                characterDto.setName(c.getName().toLowerCase());
                                characterDto.setLevel(c.getLevel());
                                characterDto.setPlayableClass(c.getPlayableClass().getName());
                                characterDto.setRace(c.getPlayableRace().getName());
                                characterDto.setSlug(c.getRealm().getName());
                                characterDto.setRealm(c.getRealm().getSlug());
                                characterDto.setFaction(c.getFaction().getName());
                                characterDto.setGender(c.getGender().getName());
                                return characterDto;
                            })
                    .collect(Collectors.toList());
        } else {
            // 리스트가 여러개라면 반복문을 통해 reqDto를 뽑아낸다.
            for (WowAccountDto dto : wowAccountDto) {
                ActualCharacterDto characterDto = new ActualCharacterDto();
                dto.getCharacters()
                        .stream()
                        .filter(c -> c.getLevel() > 50)
                        .map(
                                c -> {
                                    characterDto.setCharacterSeNumber(c.getId());
                                    characterDto.setAccountId(accountEntity.getAccountId());
                                    characterDto.setName(c.getName().toLowerCase());
                                    characterDto.setLevel(c.getLevel());
                                    characterDto.setPlayableClass(c.getPlayableClass().getName());
                                    characterDto.setRace(c.getPlayableRace().getName());
                                    characterDto.setSlug(c.getRealm().getName());
                                    characterDto.setRealm(c.getRealm().getSlug());
                                    characterDto.setFaction(c.getFaction().getName());
                                    return characterDto;
                                });
                requestList.add(characterDto);
            }
            ;
        }
        ;
        log.debug("SpecInfReqDto is {}", requestList);

        List<ActualCharacterDto> dtoList = new ArrayList<>();
        for (ActualCharacterDto characterDto : requestList) {
            SpecInfDto specDto = wowClient.getCharacterSpecInf(namespace, bzToken, locale, characterDto.getRealm(), characterDto.getName());
            log.debug("SpecInfRes is {}", specDto);
            if (!ObjectUtils.isEmpty(specDto.getCovenant_progress())) {

                // insert를 하기 전 DB에 데이터 검증
                CharacterEntity targetEntity = characterRespository.findCharacterByCharacterSeNumber(characterDto.getCharacterSeNumber());
                Long targetCharacterId = null;
                boolean isCreated = false;

                if (!ObjectUtils.isEmpty(targetEntity)) {
                    targetCharacterId = targetEntity.getCharacterId();
                    isCreated = true;
                }
                // 엔티티를 위한 정보를 dto 하나에 합치기
                characterDto.setSpecialization(specDto.getActive_spec().getName());
                characterDto.setEquippedItemLevel(specDto.getEquipped_item_level());
                characterDto.setAverageItemLvel(specDto.getAverage_item_level());
                characterDto.setExpansionOption(specDto.getCovenant_progress().getChosenCovenant().getName());
                characterDto.setExpansionOptionLevel(specDto.getCovenant_progress().getRenownLevel());
                dtoList.add(characterDto);

                CharacterEntity characterEntity = CharacterEntity.builder()
                        .accountEntity(accountEntity)
                        .characterId(isCreated ? targetCharacterId : null)
                        .characterSeNumber(characterDto.getCharacterSeNumber())
                        .name(characterDto.getName())
                        .level(characterDto.getLevel())
                        .playableClass(characterDto.getPlayableClass())
                        .specialization(characterDto.getSpecialization())
                        .race(characterDto.getRace())
                        .gender(characterDto.getGender())
                        .faction(characterDto.getFaction())
                        .equippedItemLevel(characterDto.getEquippedItemLevel())
                        .averageItemLvel(characterDto.getAverageItemLvel())
                        .slug(characterDto.getSlug())
                        .lastCrawledAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .expansionOption(characterDto.getExpansionOption())
                        .expansionOptionLevel(characterDto.getExpansionOptionLevel())
                        .build();

                characterRespository.save(characterEntity);
            }
            // Battle.Net API의 특성상 텀을 두고 Request를 보내야한다.
            Thread.sleep(2500L);
        }
        log.debug("진짜진짜진짜Entity를 위한 DTO is {}", dtoList);


        return true;
    }

    /**
     * 계정의 모든 캐릭터 정보를 가져온다.
     *
     * @return
     * @throws JsonProcessingException
     */
    public List<ActualCharacterDto> getMyCharacter() throws JsonProcessingException {
        // Token 및 acId 가져오기
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        long accountId = redisUtils.getSession(token).getAccountId();
        List<CharacterEntity> myCharacters =
                characterRespository.findAllByAccountEntity_AccountIdOrderByEquippedItemLevel(accountId);

        CharacterMapperImpl mapper = new CharacterMapperImpl();

        List<ActualCharacterDto> resultList = new ArrayList<>();
        for (CharacterEntity entity : myCharacters) {
//            Entity to DTO 변환
            ActualCharacterDto dto = mapper.characterEntityToDto(entity);
            resultList.add(dto);
        }
        return resultList;

    }

    /**
     * 블리자드 API를 통해
     * 캐릭터의 어둠땅/격전의 아제로스 레이드 정보를 가져온다.
     *
     * @return
     */
    public Boolean getRaidEncounter() throws Exception {
        // Token 및 acId 가져오기
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String bzToken = redisUtils.getSession(token).getBzAccessToken();
        long accountId = redisUtils.getSession(token).getAccountId();
        List<CharacterEntity> myCharacters =
                characterRespository.findAllByAccountEntity_AccountIdOrderByEquippedItemLevel(accountId);

        // 캐릭터 정보들 Stream을 돌려 RaidEncounter List 생성
        List<RaidInfDto> accountRaidInfList = myCharacters.stream()
                .map(
                        c -> {
                            RaidInfDto result =
                                    wowClient.getRaidEncounter(namespace, bzToken, locale, convertUtils.krSlugToEng(c.getSlug()),
                                            c.getName());
                            try {
                                // BlizzardAPI의 빠른 호출 방지를 위한...
                                Thread.sleep(3000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            result.setCharacterId(c.getCharacterId());
                            return result;
                        }
                ).collect(Collectors.toList());
        // 필터링
        log.debug("accountRaidInfList is {}", accountRaidInfList);
        accountRaidInfList = accountRaidInfList.stream()
                .filter(raidInfList -> raidInfList.getExpansions() != null && !ObjectUtils.isEmpty(raidInfList))
                .collect(Collectors.toList());

        for (RaidInfDto infDto : accountRaidInfList) {
            if (infDto.getExpansions() != null) {
                infDto.setExpansions(infDto.getExpansions().stream()
                        // 확장팩 범위는 어둠땅
                        .filter(
                                e -> e != null
                                        && (e.getExpansion().getName().equals("Shadowlands")
                                        || e.getExpansion().getName().equals("Battle for Azeroth")
//                                        || e.getExpansion().getName().equals("Legion")
                                )
                        )
//                        .filter(e -> e != null && e.getExpansion().getName().equals("Battle for Azeroth"))
//                        .filter(e -> e != null && e.getExpansion().getName().equals("Legion"))
                        .collect(Collectors.toList()));
            }
        }
        ;


        // stream으론 답이 안나온다.. 중첩된 For문을 사용하는 수밖에...
        List<RaidEncounterDto> encounterDtoList = new ArrayList<>();
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


                        List<ModesDto> difficultyList = instancesDto.getModes().stream()
                                .filter(m -> !m.getDifficulty().getType().equals("LFR"))
                                .collect(Collectors.toList()); // LFR = 공찾
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

                        long encounterId = !ObjectUtils.isEmpty(encounterDto) ? encounterDto.getEncounterId() : null;

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

    public List<CharacterRaidInfoDto> getSpecificCharacterRaidInfo(long characterId) throws Exception {

        return raidEncounterRepository.findCharacterRaidInfoByCharacterId(characterId);
    }

    ;
}
