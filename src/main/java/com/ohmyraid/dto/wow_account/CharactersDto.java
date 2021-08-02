package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class CharactersDto {

    public String name;
    public int id;
    public RealmDto realm;
    public PlayableClassDto playableClass;
    public PlayableRaceDto playableRace;
    public GenderDto gender;
    public FactionDto faction;
    public int level;
}
