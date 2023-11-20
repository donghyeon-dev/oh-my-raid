package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * oauth/token의 결과물을 받아오는 DTO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDto implements Serializable {


    private String access_token;

    private String token_type;

    private int expires_in;

    private String sub;
}
