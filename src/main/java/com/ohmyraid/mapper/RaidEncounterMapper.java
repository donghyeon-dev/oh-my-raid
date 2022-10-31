package com.ohmyraid.mapper;

import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RaidEncounterMapper {

    RaidEncounterMapper INSTANCEINSTANCE = Mappers.getMapper(RaidEncounterMapper.class);

    @Mapping(source = "characterId", target = "characterEntity.characterId")
    RaidEncounterEntity raidEncounterDtoToEntity(RaidEncounterDto characterDto);

    @Mapping(source = "characterEntity.characterId", target = "characterId")
    RaidEncounterDto raidEncounterEntityToDto(RaidEncounterEntity characterEntity);


}
