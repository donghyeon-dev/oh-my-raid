package com.ohmyraid.repository.raid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.domain.character.QCharacterEntity;
import com.ohmyraid.domain.raid.QRaidEncounterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.QCharacterRaidInfoDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Test
    void findByENcounterID_QueryDSL테스트() {
        // 187 NORMAL BattleForAzeroth Crucible of Storms 8/8 1
        long encounterId = 558;
        RaidEncounterEntity targetEntity = repository.findByEncounterId(encounterId);


        assertEquals(targetEntity.getInstanceName(), "Crucible of Storms");

    }

    @Test
    void getCharacterRaidInfo_querydsl_test() {
        long characterId = 6;
        QRaidEncounterEntity raidEncounterEntity = QRaidEncounterEntity.raidEncounterEntity;
        QCharacterEntity characterEntity = QCharacterEntity.characterEntity;

        List<CharacterRaidInfoDto> raidInfoDtoList = jpaQueryFactory
                .select(new QCharacterRaidInfoDto(
                        raidEncounterEntity.encounterId,
                        raidEncounterEntity.characterEntity.characterId.as("characterId"),
                        characterEntity.name.as("characterName"),
                        characterEntity.slug.as("slugName"),
                        raidEncounterEntity.difficulty,
                        raidEncounterEntity.expansionName,
                        raidEncounterEntity.instanceName,
                        raidEncounterEntity.lastCrawledAt,
                        raidEncounterEntity.progress
                ))
                .from(raidEncounterEntity)
                .innerJoin(characterEntity).on(raidEncounterEntity.characterEntity.characterId.eq(characterEntity.characterId)
                        .and(characterEntity.characterId.eq(characterId)))
                .fetch();

        raidInfoDtoList.forEach(System.out::println);

    }


}