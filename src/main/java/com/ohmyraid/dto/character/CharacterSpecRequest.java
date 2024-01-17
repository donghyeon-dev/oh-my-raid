package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ohmyraid.common.enums.SlugType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterSpecRequest {

    @ApiModelProperty(value = "서버명", name = "slugName", example="아즈샤라")
    private String slugName;

    @ApiModelProperty(value = "캐릭터명", name = "characterName")
    private String characterName;
}
