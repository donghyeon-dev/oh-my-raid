package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import com.ohmyraid.dto.wow_account.SpecInfDto;
import com.ohmyraid.dto.wow_account.WowAccountDto;
import com.ohmyraid.feign.RaiderClient;
import com.ohmyraid.feign.WowClient;
import com.ohmyraid.mapper.CharacterMapperImpl;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
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

    private final RaiderClient raiderClient;

    private final WowClient wowClient;

    private final RedisUtils redisUtils;

    private final ObjectMapper mapper;

    @Value("${bz.namespace}")
    private final String namespace = null;

    @Value("${bz.locale}")
    private final String locale = null;

    @Value("${bz.region}")
    private final String region = null;

    /**
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
                    .filter(c -> c.getLevel() > 30)
                    .map(
                            c -> {
                                ActualCharacterDto characterDto = new ActualCharacterDto();
                                characterDto.setCharacterSeNumber(c.getId());
                                characterDto.setAccountEntity(accountEntity);
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
                                    characterDto.setAccountEntity(accountEntity);
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

                // 엔티티를 위한 정보를 dto 하나에 합치기
                characterDto.setSpecialization(specDto.getActive_spec().getName());
                characterDto.setEquippedItemLevel(specDto.getEquipped_item_level());
                characterDto.setAverageItemLvel(specDto.getAverage_item_level());
                characterDto.setExpansionOption(specDto.getCovenant_progress().getChosenCovenant().getName());
                characterDto.setExpansionOptionLevel(specDto.getCovenant_progress().getRenownLevel());
                dtoList.add(characterDto);

                // Todo 정합쳐진 DTO를 통해 Entity에 입력
                CharacterEntity characterEntity = CharacterEntity.builder()
                        .accountEntity(accountEntity)
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
            Thread.sleep(10000L);
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
            ActualCharacterDto dto = mapper.characterEntityToDto(entity);
            resultList.add(dto);
        }
        return resultList;

    }
}
