package com.ohmyraid.dto.wow_raid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Difficulty {

    @ApiModelProperty(value = "영문 난이도명", name = "type")
    private String type;

    @ApiModelProperty(value = "한글 난이도명", name = "name")
    private String name;
}
