package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.character.QCharacterEntity;
import com.ohmyraid.domain.raid.QRaidEncounterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.QCharacterRaidInfoDto;
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
    public List<CharacterRaidInfoDto> findEncounterByCharacterId(long characterId) {
        QRaidEncounterEntity raidEncounterEntity = QRaidEncounterEntity.raidEncounterEntity;
        QCharacterEntity characterEntity = QCharacterEntity.characterEntity;
        return queryFactory
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
    }
}
