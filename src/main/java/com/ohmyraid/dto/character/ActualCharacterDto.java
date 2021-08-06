package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * CharacterEntity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualCharacterDto {

    private long characterId;

    private long accountId;

    private long characterSeNumber;

    private String name;

    private int level;

    private String playbleClass;

    private String specialization;

    private String race;

    private int equippedItemLevel;

    private int averageItemLvel;

    private String slug;

    private String faction;

    private String expansionOption;

    private int expansionOptionLevel;


}
