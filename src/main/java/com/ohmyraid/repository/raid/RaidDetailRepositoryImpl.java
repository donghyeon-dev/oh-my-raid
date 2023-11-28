package com.ohmyraid.repository.raid;

import com.ohmyraid.common.enums.DifficultyType;
import com.ohmyraid.domain.character.QCharacterEntity;
import com.ohmyraid.domain.raid.QRaidDetailEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.character.QCharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.BooleanNode;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RaidDetailRepositoryImpl implements RaidDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QRaidDetailEntity raidDetail = QRaidDetailEntity.raidDetailEntity;
    private final QCharacterEntity character = QCharacterEntity.characterEntity;



    @Override
    public Long findRaidDetailIdByDto(RaidDetailDto requestDto) {

        return queryFactory
                .select(raidDetail.detailId)
                .from(raidDetail)
                .where(raidDetail.characterEntity.characterId.eq(requestDto.getCharacterId())
                        .and(raidDetail.difficulty.eq(requestDto.getDifficulty()))
                        .and(raidDetail.bossId.eq(requestDto.getBossId()))
                ).fetchOne();

    }

    @Override
    public List<CharacterRaidInfoDto> findRaidDetailListBySearchParam(CharacterRaidInfoRequest raidInfoRequest) {

        return queryFactory.select(new QCharacterRaidInfoDto(
                raidDetail.detailId,
                character.name,
                raidDetail.expansionName,
                raidDetail.instanceName,
                raidDetail.difficulty,
                raidDetail.bossName,
                raidDetail.completedCount,
                raidDetail.lastKilledAt,
                raidDetail.lastCrawledAt
        )).from(raidDetail)
        .innerJoin(character).on(character.characterId.eq(raidDetail.characterEntity.characterId))
        .where(eqCharacterId(raidInfoRequest.getCharacterId())
            , eqExpansionId(raidInfoRequest.getExpansionId())
            , eqDifficulty(raidInfoRequest.getDifficulty())
        )
        .fetch();
    }

    private BooleanExpression eqCharacterId(Integer characterId){
        if(ObjectUtils.isEmpty(characterId)){
            return null;
        }
        return character.characterId.eq(characterId.longValue());
    }

    private BooleanExpression eqExpansionId(Integer expansionId){
        if(ObjectUtils.isEmpty(expansionId)){
            return null;
        }
        return raidDetail.expansionId.eq(expansionId.longValue());
    }

    private BooleanExpression eqDifficulty(Integer difficulty){
        if(ObjectUtils.isEmpty(difficulty)){
            return null;
        }
        return raidDetail.difficulty.eq(DifficultyType.getTypeById(difficulty).getDifficultyEnglishName());
    }


}
