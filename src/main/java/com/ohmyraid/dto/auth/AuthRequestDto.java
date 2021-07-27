package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestDto implements Serializable {

    private String redirect_uri;

    private String scope;

    private String grant_type;

    private String code;
}
