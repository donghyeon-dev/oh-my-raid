package com.ohmyraid.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 로그인 인풋 Vo
 */
@Data
public class LoginInpDto {

    @ApiModelProperty(example = "donghyeondev@gmail.com")
    private String email;

    @ApiModelProperty(example = "qkrgus0712!")
    private String password;
}
