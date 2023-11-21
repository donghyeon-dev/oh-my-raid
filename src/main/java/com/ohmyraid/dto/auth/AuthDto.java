package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * BlizzardAccessToken stored in Redis DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto {

    private String access_token;

    private String token_type;

    private int expires_in;

    private String sub;
}
