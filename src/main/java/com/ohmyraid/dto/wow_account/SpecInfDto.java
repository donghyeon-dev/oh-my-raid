package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecInfDto {

    public int id;
    public String name;
    public ActiveSpec active_spec;
    public int level;
    public long last_login_timestamp;
    public int average_item_level;
    public int equipped_item_level;
    public CovenantProgress covenant_progress;
}
