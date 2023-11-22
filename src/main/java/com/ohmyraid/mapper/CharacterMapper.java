package com.ohmyraid.mapper;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.CharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(uses = {CharacterResolver.class})
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(source = "characterId", target = "accountEntity.accountId")
    CharacterEntity characterDtoToEntity(CharacterDto characterDto);

    @Mapping(target = "realm", ignore = true)
    @Mapping(source = "accountEntity.accountId", target = "accountId")
    CharacterDto characterEntityToDto(CharacterEntity characterEntity);

    List<CharacterDto> characterEntitiesToDtoList(List<CharacterEntity> characterEntity);

    List<CharacterEntity> dtoListToCharacterEntities(List<CharacterDto> characterEntity);


}
