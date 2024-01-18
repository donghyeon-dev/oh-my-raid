package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Gender {

    @ApiModelProperty(value = "성별타입(ENG)", name = "type", example = "FEMALE")
    String type;

    @ApiModelProperty(value = "성별명(KOR)", name = "name")
    String name;
}
