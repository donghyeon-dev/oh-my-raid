package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("chosen_covenant")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChosenCovenant {

    private String name;
    private int id;
}
