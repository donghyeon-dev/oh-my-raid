package com.ohmyraid.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.auth.AuthDto;
import com.ohmyraid.dto.auth.AuthRequestDto;
import com.ohmyraid.feign.BattlenetClient;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class BlizzardTokenFetcher {

    private final BattlenetClient battlenetClient;

    @Value("${bz.grant-type}")
    private String GRANT_TYPE;

    /**
     * 블리자드에 액세스 토큰을 요청하는 메소드.
     *
     * <p>
     * 인증 정보를 사용하여 블리자드로부터 액세스 토큰을 요청하고, 획득한 토큰은 Redis에 저장합니다.
     * 획득한 액세스 토큰을 반환합니다.
     *
     * @return 블리자드로부터 획득한 액세스 토큰
     * @throws JsonProcessingException JSON 처리 중 발생하는 예외
     */
    public String fetchBlizzardAccessToken() throws JsonProcessingException {

        AuthDto blizzardAuthResponse = battlenetClient.getAccessTokenByClientCredential(CryptoUtils.getAuthValue(),
                AuthRequestDto.builder().grant_type(GRANT_TYPE).build());
        if(ObjectUtils.isEmpty(blizzardAuthResponse)){
            log.error("battlenetClient.getAccessTokenByClientCredential response is empty.");
            throw new IllegalStateException("Result of requsting access token is empty");
        }
        return blizzardAuthResponse.getAccess_token();
    };
}
