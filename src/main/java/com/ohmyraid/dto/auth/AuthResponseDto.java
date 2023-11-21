package com.ohmyraid.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BlizzardAccessToken stored in Redis DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private String access_token;

    private String token_type;

    private int expires_in;

    private String scope;
}
