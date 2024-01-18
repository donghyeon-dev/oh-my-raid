package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlayableRace {

    @ApiModelProperty(value = "종족명(KOR)", name = "name")
    String name;

    @ApiModelProperty(value = "종족 고유 값", name = "id")
    int id;
}
