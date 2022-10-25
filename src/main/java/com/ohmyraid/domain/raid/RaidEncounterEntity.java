package com.ohmyraid.domain.raid;

import com.ohmyraid.domain.character.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "OMR_RAID")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaidEncounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encounter_id")
    private Long encounterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    CharacterEntity characterEntity;

    @Column(length = 100, nullable = false)
    String expansionName;

    @Column(length = 100, nullable = false)
    String instanceName;

    @Column(length = 12, nullable = false)
    String difficulty;

    @Column(length = 12, nullable = false)
    String progress;

    @Column(length = 12, nullable = false)
    LocalDateTime lastCrawledAt;
}
