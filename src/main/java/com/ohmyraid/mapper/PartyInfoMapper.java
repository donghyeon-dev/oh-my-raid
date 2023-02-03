package com.ohmyraid.mapper;

import com.ohmyraid.domain.party.PartyInfoEntity;
import com.ohmyraid.dto.party.PartyInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PartyInfoMapper {

    PartyInfoMapper INSTANCE = Mappers.getMapper(PartyInfoMapper.class);

    @Mapping(source = "createAccountId", target = "createAccountId.accountId")
    PartyInfoEntity partInfoDtoToEntity(PartyInfoDto partyInfoDto);

    @Mapping(source = "createAccountId.accountId", target = "createAccountId")
    PartyInfoDto entityToDto(PartyInfoEntity entity);

    List<PartyInfoDto> entityListToDtoList(List<PartyInfoEntity> entity);

    List<PartyInfoEntity> dtoListToEntityList(List<PartyInfoDto> partyInfoDto);
}
