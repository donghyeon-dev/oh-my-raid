package com.ohmyraid.repository.raid;

import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;

import java.util.List;

public interface RaidEncounterRepositoryCustom {
//    List<CharacterRaidInfoDto> findCharacterRaidInfoByCharacterId(long characterId, long accountId);

    RaidEncounterDto findRaidEncounterEntityByDto(RaidEncounterDto requestDto);
}
