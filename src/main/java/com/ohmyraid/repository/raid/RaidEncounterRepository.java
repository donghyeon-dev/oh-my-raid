package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidEncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidEncounterRepository extends JpaRepository<RaidEncounterEntity, Long> {
    
}
