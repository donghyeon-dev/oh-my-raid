package com.ohmyraid.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
