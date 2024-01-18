package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Faction {

    @ApiModelProperty(value = "진영명(ENG)", name = "type", example = "HORDE")
    String type;

    @ApiModelProperty(value = "진영명(KOR)", name = "name", example = "호드")
    String name;
}
