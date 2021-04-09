package com.ohmyraid.vo.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 로그인 인풋 Vo
 */
@Data
public class LoginInpVo {

    private String email;
    private String password;
}
