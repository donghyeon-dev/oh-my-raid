package com.ohmyraid.mapper;

import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.wow_account.CharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(target = "accountEntity", expression = "java(mappingAccount(characterDto.getAccountId()))")
    CharacterEntity characterDtoToEntity(CharacterDto characterDto);

    @Mapping(target = "realm", ignore = true)
    @Mapping(source = "accountEntity.accountId", target = "accountId")
    CharacterDto characterEntityToDto(CharacterEntity characterEntity);

    List<CharacterDto> characterEntitiesToDtoList(List<CharacterEntity> characterEntity);

    List<CharacterEntity> dtoListToCharacterEntities(List<CharacterDto> characterEntity);

    default AccountEntity mappingAccount(long accountId){
        if(accountId == 0){
            return null;
        }

        return AccountEntity.builder().accountId(accountId).build();


    }

}
