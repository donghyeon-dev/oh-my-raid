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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("modes")
public class ModesDto {

    @ApiModelProperty(value = "난이도 정보 DTO", name = "difficulty")
    private Difficulty difficulty;

    @ApiModelProperty(value = "레이드 완료여부 DTO", name = "status")
    private Status status;

    @ApiModelProperty(value = "레이드 진척도 DTO", name = "progress")
    private ProgressDto progress;
}
