package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CharacterRaidInfoRequest {

    @ApiModelProperty(value = "캐릭터 고유 ID", name = "characterId", example="19")
    private Integer characterId;

    @ApiModelProperty(value = "확장팩 ID (eg.395:Legion, 396:BFA, 499:SHL, 503:DF)", name = "expansionId"
            , allowableValues = "395,396,499,503", example="395")
    private Integer expansionId;

    @ApiModelProperty(value = "난이도 ID", name = "difficulty", allowableValues = "1,2,3,4", example="4")
    private Integer difficulty;

}
