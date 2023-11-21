package com.ohmyraid.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.dto.auth.*;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.feign.BattlenetClient;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {

    private final RedisUtils redisUtils;
    private final BattlenetClient battlenetClient;

    @Value("${bz.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${bz.summary-grant-type}")
    private String SUMMARY_GRANT_TYPE;

    @Value("${bz.normal-grant-type}")
    private String NORMAL_GRANT_TYPE;


    @Value("${bz.scope}")
    private String SCOPE;

    public String getAccessTokenWithAuthorizationCode(String code) throws JsonProcessingException {
        String AUTH = CryptoUtils.getAuthValue();

        AuthorizationCodeAuthRequestDto authRequestDto = AuthorizationCodeAuthRequestDto.builder()
                .grant_type(SUMMARY_GRANT_TYPE)
                .redirect_uri(REDIRECT_URI)
                .scope(SCOPE)
                .code(code)
                .build();

        AuthResponseDto tokenRes = battlenetClient.getAccessTokenByAuthorizationCode(authRequestDto, AUTH);
        log.debug("TokenRes is {}", tokenRes);

        return tokenRes.getAccess_token();
    }

    ;

    public Boolean storeAccessToken(StoreAtReqDto reqDto) throws JsonProcessingException {
        String bzToken = reqDto.getAccessToken();
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        log.debug("Bllizzard Token is {}", bzToken);

        CheckTokenDto body = new CheckTokenDto();
        body.setRegion("KR");
        body.setToken(bzToken);
        Map<String, Object> checkTokenResult = battlenetClient.checkToken(body);

        if (!ObjectUtils.isEmpty(checkTokenResult)) {
            int exp = (int) checkTokenResult.get("exp");
            int now = (int) (System.currentTimeMillis() / 1000);
            if (exp > now) {
                // session에 bz AccessToken을 등록
                UserSessionDto redisDto = redisUtils.getRedisValue(token, UserSessionDto.class);
                redisDto.setBlizzardAccessToken(bzToken);
                redisUtils.storeObjectToRedis(token, redisDto);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Client Credential로 액세스 토큰을 얻는 메서드입니다.
     *
     * <p>
     * 본 메서드는 BattlenetClient를 이용하여 Client Credential에 대한 액세스 토큰을 얻습니다.
     * 액세스 토큰이 비어있는 경우, 이를 로그에 기록하고 IllegalStateException를 발생시킵니다.
     * </p>
     *
     * @return blizzardAuthResponse의 액세스 토큰을 반환합니다.
     * @throws IllegalStateException 액세스 토큰 요청의 결과가 비어있을 경우 예외를 발생시킵니다.
     */
    public String getAccessTokenWithClientCredential(){

        AuthResponseDto blizzardAuthResponse = battlenetClient.getAccessTokenByClientCredential(CryptoUtils.getAuthValue(),
                ClientCredentialAuthRequestDto.builder().grant_type(NORMAL_GRANT_TYPE).build());
        if(ObjectUtils.isEmpty(blizzardAuthResponse)){
            log.error("battlenetClient.getAccessTokenByClientCredential response is empty.");
            throw new IllegalStateException("Result of requsting access token is empty");
        }

        return blizzardAuthResponse.getAccess_token();
    };
}
