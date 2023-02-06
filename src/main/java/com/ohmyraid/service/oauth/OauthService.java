package com.ohmyraid.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
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
}
