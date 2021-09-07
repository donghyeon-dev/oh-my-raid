package com.ohmyraid.dto.wow_raid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ohmyraid.domain.character.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * RaidEncounter Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaidEncounterDto {

    private Long encounterId;

    private CharacterEntity characterEntity;

    private String expansionName;

    private String instanceName;

    private String difficulty;

    private String progress;

    private String lastCrawledAt;

}
