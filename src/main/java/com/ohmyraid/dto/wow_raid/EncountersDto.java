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
@JsonRootName("encounters")
public class EncountersDto {

    @ApiModelProperty(value = "네임드 정보", name = "encounter")
    private EncounterDto encounter;

    @ApiModelProperty(value = "네임드 처치 수", name = "comopleted_count")
    private int completed_count;

    @ApiModelProperty(value = "가장 최근에 잡은 시간", name = "last_kill_timestamp")
    private String last_kill_timestamp;


}
