package com.ohmyraid.dto.wow_raid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("progress")
public class ProgressDto {

    @ApiModelProperty(value = "잡은 네임드 수", name = "completedCount")
    private long completedCount;

    @ApiModelProperty(value = "총 네임드 수", name = "totalCount")
    private long totalCount;

    @ApiModelProperty(value = "각 네임드 별 정보DTO ", name = "encounters")
    private List<EncountersDto> encounters;


}
