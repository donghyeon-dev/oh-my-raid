package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidDetailEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidDetailRepository extends JpaRepository<RaidDetailEntity, Long>, RaidDetailRepositoryCustom{

    Long findRaidDetailIdByDto(RaidDetailDto raidDetailDto);

    List<CharacterRaidInfoDto> findRaidDetailListBySearchParam(CharacterRaidInfoRequest raidInfoRequest);
}
