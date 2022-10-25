package com.ohmyraid.repository.raid;

import com.ohmyraid.domain.raid.RaidEncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidEncounterRepository extends JpaRepository<RaidEncounterEntity, Long> {

//    RaidEncounterEntity findByInstanceNameAndCharacterEntity_CharacterIdAndDifficulty(String instanceName,
//                                                                                      long characterId,
//                                                                                      String difficulty);
//
//    @Modifying
//    @Query("UPDATE OMR_RAID r SET r.lastCrawledAt = :lca, progress= :progress  ")
//    void
//
//    default boolean fetchEntity(RaidEncounterEntity entity) {
//        RaidEncounterEntity encounterEntity = findByInstanceNameAndCharacterEntity_CharacterIdAndDifficulty(
//                entity.getInstanceName(), entity.getCharacterEntity().getCharacterId(), entity.getDifficulty()
//        );
//        if (ObjectUtils.isEmpty(encounterEntity)) {
//            save(entity);
//        } else {
//
//        }
//        return true;
//    }

}
