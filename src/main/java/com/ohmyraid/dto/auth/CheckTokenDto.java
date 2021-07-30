package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckTokenDto {

    private String region;

    private String token;
}
