package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.common.enums.SlugType;
import com.ohmyraid.config.Constant;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_raid.*;
import com.ohmyraid.feign.WowClient;
import com.ohmyraid.mapper.CharacterMapper;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.raid.RaidDetailRepository;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtilTest;
import com.ohmyraid.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

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
    private WowClient wowClient;


    @Test
    void getSpecificCharacterEncounterInfo() throws JsonProcessingException {
        String bzToken = redisUtils.getRedisValue(Constant.Auth.BLIZZARD_TOKEN_KEY, String.class);
        // 박동현 가져오기
        CharacterEntity characterEntity = characterRespository.findCharacterEntityByCharacterId(19);
        CharacterDto characterDto = CharacterMapper.INSTANCE.characterEntityToDto(characterEntity);

        RaidInfoDto charactersRaidInfo = wowClient.getRaidEncounter(Constant.Auth.NAMESPACE
                , bzToken
                , Constant.Auth.LOCALE
                , SlugType.getTypeByName(characterDto.getSlug()).getSlugEnglishName(),
                characterDto.getName());

        List<ExpansionsDto> expansionRaidInfoList = charactersRaidInfo.getExpansions().stream()
                .filter(expansionsDto -> expansionsDto.getExpansion().getId() >= 395 // 395:군단
                        && !expansionsDto.getExpansion().getName().equals(Constant.CURRENT_SEASON) )
                .collect(Collectors.toList())
                ; // legion,bfa,sdl,df

        for(ExpansionsDto expansionsRaidInfo : expansionRaidInfoList){
            String expansionName = expansionsRaidInfo.getExpansion().getName(); // LEGION
            List<InstancesDto> expansionInstanceInfoList = expansionsRaidInfo.getInstances();
            for(InstancesDto raidInstancesDto : expansionInstanceInfoList){
                String instanceName = raidInstancesDto.getInstance().getName(); // TOMB_OF_SARGERAS
                List<ModesDto> instanceModeList = raidInstancesDto.getModes();
                for(ModesDto instanceMode : instanceModeList){
                    String instanceDifficulty = instanceMode.getDifficulty().getType();// MYTHIC
                    int currentDifficultyKilledBossesCount = instanceMode.getProgress().getCompletedCount(); // 1
                    int currentDifficultyTotalKilledBossesCount = instanceMode.getProgress().getTotalCount(); // 13
                    List<EncountersDto> instanceBossList = instanceMode.getProgress().getEncounters();
                    for(EncountersDto instanceBoss : instanceBossList ){
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


}
