package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characters {

    public String name;
    public int id;
    public Realm realm;
    public PlayableClass playableClass;
    public PlayableRace playableRace;
    public Gender gender;
    public Faction faction;
    public int level;
}
