package com.ohmyraid.mapper;

import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RaidDetailMapper {

    RaidDetailMapper INSTANCE = Mappers.getMapper(RaidDetailMapper.class);

    @Mapping(source = "characterId", target = "characterEntity.characterId")
    RaidDetailEntity raidEncounterDtoToEntity(RaidDetailDto raidDetailDto);

    @Mapping(source = "characterEntity.characterId", target = "characterId")
    RaidDetailDto raidEncounterEntityToDto(RaidDetailEntity raidDetailEntity);


}
