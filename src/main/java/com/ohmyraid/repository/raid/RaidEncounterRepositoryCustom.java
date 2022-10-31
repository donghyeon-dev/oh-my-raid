package com.ohmyraid.repository.raid;

import com.ohmyraid.dto.character.CharacterRaidInfoDto;

import java.util.List;

public interface RaidEncounterRepositoryCustom {
    List<CharacterRaidInfoDto> findEncounterByCharacterId(long characterId);
}
