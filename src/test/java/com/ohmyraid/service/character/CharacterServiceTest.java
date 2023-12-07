package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.common.enums.SlugType;
import com.ohmyraid.config.Constant;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClientWrapper;
import com.ohmyraid.mapper.CharacterMapper;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.raid.RaidDetailRepository;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CharacterServiceTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRespository characterRespository;

    @Autowired
    private RaidDetailRepository raidDetailRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WowClientWrapper wowClientWrapper;


    @Test
    void getSpecificCharacterEncounterInfo() throws JsonProcessingException {
        String bzToken = redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class);
        // 박동현 가져오기
        CharacterEntity characterEntity = characterRespository.findCharacterEntityByCharacterId(19);
        CharacterDto characterDto = CharacterMapper.INSTANCE.characterEntityToDto(characterEntity);

        RaidInfoDto charactersRaidInfo = wowClientWrapper.getRaidEncounter(Constant.Auth.NAMESPACE
                , bzToken
                , Constant.Auth.LOCALE
                , SlugType.getTypeByName(characterDto.getSlug()).getSlugEnglishName(),
                characterDto.getName());

        List<Expansions> expansionRaidInfoList = charactersRaidInfo.getExpansions().stream()
                .filter(expansions -> expansions.getExpansion().getId() >= 395 // 395:군단
                        && !expansions.getExpansion().getName().equals(Constant.CURRENT_SEASON) )
                .collect(Collectors.toList())
                ; // legion,bfa,sdl,df

        for(Expansions expansionsRaidInfo : expansionRaidInfoList){
            String expansionName = expansionsRaidInfo.getExpansion().getName(); // LEGION
            List<Instances> expansionInstanceInfoList = expansionsRaidInfo.getInstances();
            for(Instances raidInstances : expansionInstanceInfoList){
                String instanceName = raidInstances.getInstance().getName(); // TOMB_OF_SARGERAS
                List<ModesDto> instanceModeList = raidInstances.getModes();
                for(ModesDto instanceMode : instanceModeList){
                    String instanceDifficulty = instanceMode.getDifficulty().getType();// MYTHIC
                    int currentDifficultyKilledBossesCount = instanceMode.getProgress().getCompletedCount(); // 1
                    int currentDifficultyTotalKilledBossesCount = instanceMode.getProgress().getTotalCount(); // 13
                    List<Encounters> instanceBossList = instanceMode.getProgress().getEncounters();
                    for(Encounters instanceBoss : instanceBossList ){
                        String bossName = instanceBoss.getEncounter().getName(); // Kiljeden
                        long bossId = instanceBoss.getEncounter().getId();
                        int currentBossKills = instanceBoss.getCompleted_count(); // 1
                        LocalDateTime lastKilledAt = DatetimeUtils.unixToLocalDateTime(Long.parseLong(instanceBoss.getLast_kill_timestamp())); // 2023.01.01 12:31:33
                        RaidDetailEntity raidDetailEntity = RaidDetailEntity.builder()
                                .characterEntity(characterEntity)
                                .expansionName(expansionName)
                                .difficulty(instanceDifficulty)
                                .instanceName(instanceName)
                                .bossName(bossName)
                                .bossId(bossId)
                                .completedCount(currentBossKills)
                                .lastCrawledAt(DatetimeUtils.now())
                                .lastKilledAt(lastKilledAt)
                                .build();
                        raidDetailRepository.save(raidDetailEntity);
                    }
                }
            }
        }


    }

    @Test
    void findRaidDetailIdByDto_테스트(){
        RaidDetailDto raidDetailDto = RaidDetailDto.builder()
                .bossId(2168)
                .difficulty("MYTHIC")
                .characterId(19L)
                .build();

        Long detailId = raidDetailRepository.findRaidDetailIdByDto(raidDetailDto);
        assertEquals(detailId, 166);

        raidDetailDto.setBossId(2374);
//        detailId = Optional.ofNullable(raidDetailRepository.findRaidDetailIdByDto(raidDetailDto)).orElse(0L);
        detailId = raidDetailRepository.findRaidDetailIdByDto(raidDetailDto);
        log.info("What is result of querydsl can't find value. detailId = {}", detailId);
    }


    @Test
    void findRaidDetailListBySearchParam(){

        List<CharacterRaidInfoDto> raidDetailDtoList = raidDetailRepository.findRaidDetailListBySearchParam(CharacterRaidInfoRequest.builder()
                        .characterId(19)
                .build());
        log.info("raidDetailDtoList={}",raidDetailDtoList.toString());
    }

    @Test
    void getRaidDetail_mockmvc_테스트() throws Exception {
            mockMvc.perform(get("/account/1/raid-encounter").header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvaG15cmFpZCIsImlhdCI6MTcwMTc1MTE5OCwiTG9naW5JZCI6ImRvbmdoeWVvbmRldkBnbWFpbC5jb20iLCJVc2VyTmFtZSI6ImF1dG9jYXQiLCJleHAiOjE3MDE3ODcxOTh9.KP-fa5aJdw73_2imb8_3btqgHYmTWjxek92vFBEQZWo"))
                    .andDo(print());
    }
}
