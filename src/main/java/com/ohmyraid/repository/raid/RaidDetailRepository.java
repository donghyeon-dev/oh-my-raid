package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidEncounterDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidDetailRepository extends JpaRepository<RaidDetailEntity, Long>, RaidDetailRepositoryCustom{

    Long findRaidDetailIdByDto(RaidDetailDto raidDetailDto);

//    List<RaidDetailEntity> findRaidDetailListBySearchParam();
}
