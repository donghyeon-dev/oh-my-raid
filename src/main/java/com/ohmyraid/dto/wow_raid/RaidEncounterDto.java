package com.ohmyraid.dto.wow_raid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * RaidEncounter Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RaidEncounterDto {

    private Long encounterId;

    private long characterId;

    private String expansionName;

    private String instanceName;

    private String difficulty;

    private String progress;

    private LocalDateTime lastCrawledAt;

    @QueryProjection
    public RaidEncounterDto(Long encounterId, long characterId, String expansionName, String instanceName, String difficulty, String progress, LocalDateTime lastCrawledAt) {
        this.encounterId = encounterId;
        this.characterId = characterId;
        this.expansionName = expansionName;
        this.instanceName = instanceName;
        this.difficulty = difficulty;
        this.progress = progress;
        this.lastCrawledAt = lastCrawledAt;
    }
}
