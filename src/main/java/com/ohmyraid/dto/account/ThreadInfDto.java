package com.ohmyraid.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ohmyraid.dto.auth.AuthDto;
import lombok.Data;

/**
 * ThreadLocal에 담길 토큰 VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreadInfDto {

    // OMR 웹사이트의 AccessToken
    private String accessToken;

}
