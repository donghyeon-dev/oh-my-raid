package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.character.QCharacterEntity;
import com.ohmyraid.domain.raid.QRaidDetailEntity;
import com.ohmyraid.domain.raid.QRaidEncounterEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.QCharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.QRaidEncounterDto;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RaidDetailRepositoryImpl implements RaidDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Long findRaidDetailIdByDto(RaidDetailDto requestDto) {
        return 0L;
//        QRaidDetailEntity raidDetailEntity = QRaidDetailEntity.raidDetailEntity;
//
//        return queryFactory
//                .select(new QRaidDetailEntity(
//                        raidDetailEntity.detailId
//                ))
//                .from(raidDetailEntity)
//                .where(raidDetailEntity.characterEntity.characterId.eq(requestDto.getCharacterId())
//                        .and(raidDetailEntity.difficulty.eq(requestDto.getDifficulty()))
//                        .and(raidDetailEntity.bossId.eq(requestDto.getBossId()))
//                ).fetchOne();

    }
}
