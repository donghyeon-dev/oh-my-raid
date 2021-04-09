package com.ohmyraid.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class SignUpInpVo {

    @ApiModelProperty(value = "로그인이메일", name = "email")
    private String email;

    @ApiModelProperty(value = "패스워드", name = "password")
    private String password;

    @ApiModelProperty(value = "별명", name = "nickname")
    private String nickname;
}
