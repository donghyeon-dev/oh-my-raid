package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.QCharacterEntity;
import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.dto.wow_account.QCharacterDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CharacterRepositoryImpl implements CharacterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QCharacterEntity character = QCharacterEntity.characterEntity;


    @Override
    public List<CharacterDto> findCharacterDtosByUserId(long userId) {
        return queryFactory.select(new QCharacterDto(
                character.characterId,
                character.name,
                character.slug
        )).from(character)
                .where(character.userEntity.userId.eq(userId)).fetch();
    }

    @Override
    public CharacterDto findCharacterDtoBySlugAndName(CharacterSpecRequest request) {
        return queryFactory.select(new QCharacterDto(
                character.characterId,
                character.name,
                character.slug
        )).from(character)
                .where(character.slug.eq(request.getSlugName())
                        .and(character.name.eq(request.getCharacterName())))
                .fetchOne();
    }
}
