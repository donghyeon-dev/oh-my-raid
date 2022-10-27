package com.ohmyraid.mapper;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper//(uses = {CharacterResolver.class})
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(target = "characterId", constant = "0L")
    CharacterEntity characterDtoToEntity(ActualCharacterDto characterDto);

    @Mapping(target = "realm", ignore = true)
    @Mapping(source = "accountEntity.accountId", target = "accountId")
    ActualCharacterDto characterEntityToDto(CharacterEntity characterEntity);


}
