package com.ohmyraid.dto.character;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class ScoresDto {
    private int all;
    private int dps;
    private int healder;
    private int tank;
    private int spec_0;
    private int spec_1;
    private int spec_2;
    private int spec_3;

}
