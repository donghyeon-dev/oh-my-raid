package com.ohmyraid.dto.party;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * PartyInfoEntity Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class PartyInfoDto {

    @ApiModelProperty(value = "파티아이디", name = "partyId")
    private Long partyId;

    @ApiModelProperty(value = "레이드인스턴스명", name = "instanceName")
    private String instanceName;

    @ApiModelProperty(value = "제목", name = "subject")
    private String subject;

    @ApiModelProperty(value = "난이도", name = "difficulty")
    private String difficulty;

    @ApiModelProperty(value = "인원수", name = "requiredMemers")
    private int requiredMembers;

    @ApiModelProperty(value = "탐수", name = "times")
    private int times;

    @ApiModelProperty(value = "확고/학원/트라이", name = "memberCapacity")
    private String memberCapacity;

    @ApiModelProperty(value = "내용", name = "contents")
    private String contents;

    @ApiModelProperty(value = "출발시간", name = "startAt")
    private LocalDateTime startAt;

    @ApiModelProperty(value = "모집기간", name = "recruitUntil")
    private LocalDateTime recruitUntil;

    @ApiModelProperty(value = "서버", name = "slug")
    private String slug;

    @ApiModelProperty(value = "생성자계정ID", name = "createUserId")
    private long createUserId;

}
