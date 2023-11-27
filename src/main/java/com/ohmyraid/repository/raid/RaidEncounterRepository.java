package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidEncounterRepository extends JpaRepository<RaidEncounterEntity, Long>, RaidEncounterRepositoryCustom {

//    public List<CharacterRaidInfoDto> findCharacterRaidInfoByCharacterId(long characterId, long accountId);

    public RaidEncounterDto findRaidEncounterEntityByDto(RaidEncounterDto requestDto);

}
