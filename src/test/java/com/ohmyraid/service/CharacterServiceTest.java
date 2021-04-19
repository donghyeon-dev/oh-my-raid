package com.ohmyraid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.feign.RaiderClient;
import com.ohmyraid.vo.character.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CharacterServiceTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RaiderClient raiderClient;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getCharacterInf() throws Exception{
        // FeignClient를 통해 유저의 아이템레벨 정보를 가져온다,
        Map<String,Object> gearResult
                = raiderClient.getCharacterGear("kr",
                "azshara", "autocat", "gear",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                        "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        GearVo gearVo = mapper.convertValue(gearResult.get("gear"),GearVo.class);

        // FeignClient를 통해 유저의 RaidProgression 정보를 가져온다,
        Map<String, Object> raidResult = raiderClient.getCharacterRaidProgress("kr",
                "azshara", "autocat", "raid_progression",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                        "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        Map<String, String> rp = mapper.convertValue(raidResult.get("raid_progression"),Map.class);
        RaidPrgVo raidPrgVo = mapper.convertValue(rp.get("castle-nathria"), RaidPrgVo.class);

        // FeignClient를 통해 유저의 M+ 정보를 가져온다,
        Map<String, Object> mythicPlusResult = raiderClient.getCharacterMythicScore("kr",
                "azshara", "autocat", "mythic_plus_scores_by_season:current",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                        "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        List<Map<String, String>> mp = mapper.convertValue(mythicPlusResult.get("mythic_plus_scores_by_season"), ArrayList.class);
        MpScoreVo mpScoreVo = mapper.convertValue(mp.get(0) ,MpScoreVo.class);
        log.debug("GearVo is {}", gearVo);
        log.debug("Raid Progression is {}", raidPrgVo);
        log.debug("MythicPlusScore is {}", mpScoreVo);

    }
}
