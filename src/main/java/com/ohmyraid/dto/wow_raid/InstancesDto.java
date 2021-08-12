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
@JsonRootName("instances")
public class InstancesDto {

    @ApiModelProperty(value = "인스턴스 던전 정보 DTO", name = "instance")
    private InstanceDto instance;

    @ApiModelProperty(value = "난이도 관련 정보 DTO", name = "modes")
    private List<ModesDto> modes;
}
