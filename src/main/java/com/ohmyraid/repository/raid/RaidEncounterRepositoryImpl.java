package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.character.QCharacterEntity ;
import com.ohmyraid.domain.raid.QRaidEncounterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.QCharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.QRaidEncounterDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RaidEncounterRepositoryImpl implements RaidEncounterRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public RaidEncounterEntity findByEncounterId(long encounterId) {
        QRaidEncounterEntity raidEncounter = QRaidEncounterEntity.raidEncounterEntity;
        RaidEncounterEntity targetEntity = queryFactory.selectFrom(raidEncounter)
                .where(raidEncounter.encounterId.eq(encounterId))
                .fetchOne();
        return targetEntity;
    }


    @Override
    public RaidEncounterDto findRaidEncounterEntityByDto(RaidEncounterDto requestDto) {
        QRaidEncounterEntity raidEncounterEntity = QRaidEncounterEntity.raidEncounterEntity;

        return queryFactory
                .select(new QRaidEncounterDto(
                        raidEncounterEntity.encounterId,
                        raidEncounterEntity.characterEntity.characterId.as("characterId"),
                        raidEncounterEntity.expansionName,
                        raidEncounterEntity.instanceName,
                        raidEncounterEntity.difficulty,
                        raidEncounterEntity.progress,
                        raidEncounterEntity.lastCrawledAt
                ))
                .from(raidEncounterEntity)
                .where(raidEncounterEntity.characterEntity.characterId.eq(requestDto.getCharacterId())
                        .and(raidEncounterEntity.difficulty.eq(requestDto.getDifficulty()))
                        .and(raidEncounterEntity.instanceName.eq(requestDto.getInstanceName()))
                        .and(raidEncounterEntity.expansionName.eq(requestDto.getExpansionName()))
                ).fetchOne();

    }
}
