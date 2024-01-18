package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActiveSpec {

    @ApiModelProperty(value = "전문화명(한글)", name = "name")
    private String name;

    @ApiModelProperty(value = "전문화 고유 값", name = "id")
    private int id;
}
