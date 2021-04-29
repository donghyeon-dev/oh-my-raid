package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterGearDto {

    private String name;
    private String race;
    private String classes;
    private String activeSpecName;
    private String activeSpecRole;
    private String gender;
    private String faction;
    private int archievementPoints;
    private int honorableKills;
    private String region;
    private String realm;
    private LocalDateTime lastCrawledAt;
    private GearDto gear;
}
