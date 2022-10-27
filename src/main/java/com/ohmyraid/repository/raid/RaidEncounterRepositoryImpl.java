package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.QRaidEncounterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RaidEncounterRepositoryImpl {

    private final JPAQueryFactory queryFactory;


    public RaidEncounterEntity findByEncounterId(long encounterId) {
        QRaidEncounterEntity raidEncounter = QRaidEncounterEntity.raidEncounterEntity;
        RaidEncounterEntity targetEntity = queryFactory.selectFrom(raidEncounter)
                .where(raidEncounter.encounterId.eq(encounterId))
                .fetchOne();
        return targetEntity;
    }

}
