package com.ohmyraid.repository.raid;

import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;

import java.util.List;

public interface RaidDetailRepositoryCustom {

    Long findRaidDetailIdByDto(RaidDetailDto raidDetailDto);

    List<CharacterRaidInfoDto> findRaidDetailListBySearchParam(CharacterRaidInfoRequest raidInfoRequest);

}
