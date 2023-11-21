package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationCodeAuthRequestDto implements Serializable {

    private String access_token;

    private String code;

    private String grant_type;

    private String scope;

    private String redirect_uri;

}
