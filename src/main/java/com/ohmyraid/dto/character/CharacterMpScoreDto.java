package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterMpScoreDto {

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
    private List<MpScoreDto> mythicPlusScoresBySeason;
}
