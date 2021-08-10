package com.ohmyraid.mapper;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper//(uses = {CharacterResolver.class})
public interface CharacterMapper {

//    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    CharacterEntity characterDtoToEntity(ActualCharacterDto characterDto);

    @Mapping(target = "realm", ignore = true)
    ActualCharacterDto characterEntityToDto(CharacterEntity characterEntity);


}
