package com.ohmyraid.vo.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterVo {

    private long characterId;
    private String name;
    private String race;
    private String classs;
    private String activeSpecName;
    private String activeSpecRole;
    private String gender;
    private String faction;
    private int archievementPoints;
    private int honorableKills;
    private String region;
    private String realm;
    private LocalDateTime lastCrawledAt;
}
