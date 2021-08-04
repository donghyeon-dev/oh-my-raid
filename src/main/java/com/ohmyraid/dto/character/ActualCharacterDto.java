package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualCharacterDto {

    private long characterSeNumber;

    private long characterId;

    private long accountId;

    private String name;

    private int level;

    private String playbleClass;

    private String specialization ;

    private String race;

    private int equippedItemLevel;

    private int averageItemLvel;

    private String slug;

    private String faction;

    private String expansionOption;

    private int expansionOptionLevel;


}
