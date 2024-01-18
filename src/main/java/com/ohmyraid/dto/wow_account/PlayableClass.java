package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlayableClass {

    @ApiModelProperty(value = "직업(KOR)", name = "name")
    public String name;

    @ApiModelProperty(value = "직업 고유 값", name = "id")
    public int id;
}
