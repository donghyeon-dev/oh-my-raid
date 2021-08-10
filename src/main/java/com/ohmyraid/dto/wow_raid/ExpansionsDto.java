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
@JsonRootName("expansions")
public class ExpansionsDto {

    @ApiModelProperty(value = "확장팩 관련 Dto", name = "expansionDto")
    private ExpansionDto expansion;

    @ApiModelProperty(value = "인스턴스 던전 정보 DTO", name = "instances")
    private List<InstancesDto> instances;
}
