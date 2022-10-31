package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidEncounterEntity;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidEncounterRepository extends JpaRepository<RaidEncounterEntity, Long>, RaidEncounterRepositoryCustom {

    public List<CharacterRaidInfoDto> findEncounterByCharacterId(long characterId);

}
