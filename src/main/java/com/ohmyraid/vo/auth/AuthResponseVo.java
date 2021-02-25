package com.ohmyraid.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseVo implements Serializable {

    private String accessToken;

    private String tokenType;

    private int expiresIn;

    private String scope;
}
