package com.ohmyraid.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UpdateNicknameDto {
    @ApiModelProperty(value = "로그인이메일", name = "email")
    private String email;

    @ApiModelProperty(value = "이전 닉네임", name = "currentNickname")
    private String currentNickname;

    @ApiModelProperty(value = "변경 할 닉네임", name = "changeNickname")
    private String changeNickname;

}
