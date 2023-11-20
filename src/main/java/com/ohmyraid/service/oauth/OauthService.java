package com.ohmyraid.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonExceptionHandler;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.dto.auth.AuthDto;
import com.ohmyraid.dto.auth.AuthRequestDto;
import com.ohmyraid.dto.auth.CheckTokenDto;
import com.ohmyraid.dto.auth.StoreAtReqDto;
import com.ohmyraid.dto.login.RedisDto;
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
    private final String REDIRECT_URI = null;

    @Value("${bz.grant-type}")
    private final String GRANT_TYPE = null;

    @Value("${bz.scope}")
    private final String SCOPE = null;

    public String getAccessToken(String code) throws JsonProcessingException {
        String AUTH = CryptoUtils.getAuthValue();

        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .grant_type(GRANT_TYPE)
                .redirect_uri(REDIRECT_URI)
                .scope(SCOPE)
                .code(code)
                .build();

        AuthDto tokenRes = battlenetClient.getAccessToken(authRequestDto, AUTH);
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
                RedisDto redisDto = redisUtils.getSession(token);
                redisDto.setBzAccessToken(bzToken);
                redisUtils.putSession(token, redisDto);
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

        AuthDto blizzardAuthResponse = battlenetClient.getAccessTokenByClientCredential(CryptoUtils.getAuthValue());
        if(ObjectUtils.isEmpty(blizzardAuthResponse)){
            log.error("battlenetClient.getAccessTokenByClientCredential response is empty.");
            throw new IllegalStateException("Result of requsting access token is empty");
        }

        return blizzardAuthResponse.getAccess_token();
    };
}
