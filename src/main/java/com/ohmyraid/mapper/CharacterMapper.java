package com.ohmyraid.mapper;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.character.ActualCharacterDto;
import com.ohmyraid.mapper.resolver.CharacterResolver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CharacterResolver.class})
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    CharacterEntity characterDtoToEntity(ActualCharacterDto characterDto);


    ActualCharacterDto characterEntityToDto(CharacterEntity characterEntity);
}
