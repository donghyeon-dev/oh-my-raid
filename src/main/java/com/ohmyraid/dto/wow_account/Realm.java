package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class Realm {

    @ApiModelProperty(value = "서버명(KOR)", name = "name")
    public String name;

    @ApiModelProperty(value = "서버 고유 값", name = "id")
    public int id;

    @ApiModelProperty(value = "서버명(ENG)", name = "slug")
    public String slug;

}
