package com.ohmyraid.vo.character;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class RaidPrgVo {
    private String summary;
    private int totalBosses;
    private int normalBossesKilled;
    private int heroicBossesKilled;
    private int mythicBossesKilled;
}
