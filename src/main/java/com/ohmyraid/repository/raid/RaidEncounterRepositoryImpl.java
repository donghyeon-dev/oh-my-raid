//package com.ohmyraid.repository.raid;
//
//import com.ohmyraid.domain.raid.QRaidEncounterEntity;
//import com.ohmyraid.domain.raid.RaidEncounterEntity;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@RequiredArgsConstructor
//@Repository
//public class RaidEncounterRepositoryImpl {
//
//    private final JPAQueryFactory queryFactory;
//
//    private final QRaidEncounterEntity raidEncounterEntity;
//
//    public RaidEncounterEntity findByEncounterId(long encounterId) {
//        RaidEncounterEntity targetEntity = queryFactory.selectFrom(raidEncounterEntity)
//                .where(raidEncounterEntity.encounterId.eq(encounterId))
//                .fetchOne();
//        return targetEntity;
//    }
//
//}
