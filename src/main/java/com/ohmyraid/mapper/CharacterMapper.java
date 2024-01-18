package com.ohmyraid.mapper;

import com.ohmyraid.domain.user.UserEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.CharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(target = "userEntity", expression = "java(mappingAccount(characterDto.getUserId()))")
    CharacterEntity characterDtoToEntity(CharacterDto characterDto);

    @Mapping(target = "realm", ignore = true)
    @Mapping(source = "userEntity.userId", target = "userId")
    CharacterDto characterEntityToDto(CharacterEntity characterEntity);

    List<CharacterDto> characterEntitiesToDtoList(List<CharacterEntity> characterEntity);

    List<CharacterEntity> dtoListToCharacterEntities(List<CharacterDto> characterEntity);

    default UserEntity mappingAccount(long userId){
        if(userId == 0){
            return null;
        }

        return UserEntity.builder().userId(userId).build();


    }

}
