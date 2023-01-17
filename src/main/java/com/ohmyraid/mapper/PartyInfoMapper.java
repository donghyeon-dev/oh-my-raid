package com.ohmyraid.mapper;

import com.ohmyraid.domain.party.PartyInfoEntity;
import com.ohmyraid.dto.party.PartyInpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PartyInfoMapper {

    PartyInfoMapper INSTANCE = Mappers.getMapper(PartyInfoMapper.class);

    @Mapping(source = "createAccountId", target = "createAccountId.accountId")
    PartyInfoEntity partInfoDtoToEntity(PartyInpDto partyInpDto);

    @Mapping(source = "createAccountId.accountId", target = "createAccountId")
    PartyInpDto entityToDto(PartyInfoEntity entity);
}
