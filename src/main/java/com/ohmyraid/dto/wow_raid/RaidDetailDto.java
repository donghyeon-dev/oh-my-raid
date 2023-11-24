package com.ohmyraid.dto.wow_raid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ohmyraid.domain.character.CharacterEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * RaidDetail Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RaidDetailDto {

    private Long detailId;

    private Long characterId;

    private String expansionName;

    private Long expansionId;

    private String difficulty;

    private String instanceName;

    private Long instanceId;

    private String bossName;

    private long bossId;

    private int completedCount;

    private LocalDateTime lastCrawledAt;

    private LocalDateTime lastKilledAt;

    @QueryProjection
    public RaidDetailDto(Long detailId, Long characterId, String expansionName, Long expansionId, String difficulty,
                         String instanceName, Long instanceId, String bossName, long bossId, int completedCount,
                         LocalDateTime lastCrawledAt, LocalDateTime lastKilledAt) {
        this.detailId = detailId;
        this.characterId = characterId;
        this.expansionName = expansionName;
        this.expansionId = expansionId;
        this.difficulty = difficulty;
        this.instanceName = instanceName;
        this.instanceId = instanceId;
        this.bossName = bossName;
        this.bossId = bossId;
        this.completedCount = completedCount;
        this.lastCrawledAt = lastCrawledAt;
        this.lastKilledAt = lastKilledAt;
    }
}
