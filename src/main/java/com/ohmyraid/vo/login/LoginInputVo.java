package com.ohmyraid.vo.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Embeddable;

/**
 * 로그인 인풋 Vo
 */
@Data
@Embeddable
public class LoginInputVo {

    private String email;
    private String password;
}
