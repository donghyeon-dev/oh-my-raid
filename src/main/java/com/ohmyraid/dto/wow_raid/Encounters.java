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
public class Encounters {

    @ApiModelProperty(value = "네임드 정보", name = "encounter")
    private Encounter encounter;

    @ApiModelProperty(value = "네임드 처치 수", name = "completedCount")
    private int completedCount;

    @ApiModelProperty(value = "가장 최근에 잡은 시간", name = "lastKillTimestamp")
    private String lastKillTimestamp;


}
