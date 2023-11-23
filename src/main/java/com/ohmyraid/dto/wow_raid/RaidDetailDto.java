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
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RaidDetailDto {

    private Long detailId;

    private Long characterId;

    private String expansionName;

    private String difficulty;

    private String instanceName;

    private String bossName;

    private long bossId;

    private int completedCount;

    private LocalDateTime lastCrawledAt;

    private LocalDateTime lastKilledAt;
}
