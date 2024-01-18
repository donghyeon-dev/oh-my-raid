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

    private String accessToken;

    private String tokenType;

    private int expiresIn;

    private String scope;
}
