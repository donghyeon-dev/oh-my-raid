package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreAtReqDto {

    // Blizzard API AccessToken
    private String accessToken;
}
