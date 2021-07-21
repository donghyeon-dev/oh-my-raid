package com.ohmyraid.dto.party;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyInpDto {

    @ApiModelProperty(value = "제목", name = "subject")
    private String subject;

    @ApiModelProperty(value = "난이도", name = "difficulty")
    private String difficulty;

    @ApiModelProperty( value = "인원수", name = "requiredMemers")
    private int requiredMembers;

    @ApiModelProperty(value = "탐수", name = "times")
    private int times;

    @ApiModelProperty(value = "확고/학원/트라이", name = "memberCapacity")
    private String memberCapacity;

    @ApiModelProperty(value = "내용", name = "contents")
    private String contents;

    @ApiModelProperty(value = "출발시간", name = "startAt")
    private String startAt;

    @ApiModelProperty(value = "모집기간", name = "recruitUntil")
    private String recruitUntil;
}
