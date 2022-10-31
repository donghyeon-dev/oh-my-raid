package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CharacterRaidInfoDto {

    private Long encounterId;

    private Long characterId;

    private String characterName;

    private String slugName;

    private String difficulty;

    private String expansionName;

    private String instanceName;

    private LocalDateTime lastCrawledAt;

    private String progress;

    @QueryProjection
    public CharacterRaidInfoDto(Long encounterId, Long characterId, String characterName, String slugName, String difficulty, String expansionName, String instanceName, LocalDateTime lastCrawledAt, String progress) {
        this.encounterId = encounterId;
        this.characterId = characterId;
        this.characterName = characterName;
        this.slugName = slugName;
        this.difficulty = difficulty;
        this.expansionName = expansionName;
        this.instanceName = instanceName;
        this.lastCrawledAt = lastCrawledAt;
        this.progress = progress;
    }
}
