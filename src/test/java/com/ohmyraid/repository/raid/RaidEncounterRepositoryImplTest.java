package com.ohmyraid.repository.raid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RaidEncounterRepositoryImplTest {

    private static final Logger log = LoggerFactory.getLogger(RaidEncounterRepositoryImplTest.class);

    @Autowired
    private RaidEncounterRepositoryImpl repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findByENcounterID_QueryDSL테스트() {
        // 187 NORMAL BattleForAzeroth Crucible of Storms 8/8 1
        long encounterId = 181;
        RaidEncounterEntity targetEntity = repository.findByEncounterId(encounterId);


        assertEquals(targetEntity.getInstanceName(), "Crucible of Storms");

    }


}