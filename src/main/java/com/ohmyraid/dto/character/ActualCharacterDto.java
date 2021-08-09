package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CharacterEntity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualCharacterDto {

    private long characterId;

    private long accountId;

    private int characterSeNumber;

    private String name;

    private int level;

    private String playableClass;

    private String specialization;

    private String race;

    private String gender;

    private int equippedItemLevel;

    private int averageItemLvel;

    private String slug;

    private String realm;

    private String faction;

    private String expansionOption;

    private int expansionOptionLevel;

    private LocalDateTime lastCrawledAt;


}
