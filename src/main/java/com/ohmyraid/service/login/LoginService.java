package com.ohmyraid.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.dto.auth.AuthDto;
import com.ohmyraid.dto.auth.AuthRequestDto;
import com.ohmyraid.dto.auth.CheckTokenDto;
import com.ohmyraid.dto.auth.StoreAtReqDto;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.RedisDto;
import com.ohmyraid.feign.BattlenetClient;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.JwtUtils;
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
public class LoginService {

    private final AccountRepository accountRepository;

    private final BattlenetClient battlenetClient;

    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;

    @Value("${bz.redirect-uri}")
    private final String REDIRECT_URI = null;

    @Value("${bz.grant-type}")
    private final String GRANT_TYPE = null;

    @Value("${bz.scope}")
    private final String SCOPE = null;


    /**
     * 로그인 서비스
     *
     * @param inpVo
     * @return
     * @throws JsonProcessingException
     */
    public RedisDto signIn(LoginInpDto inpVo) throws JsonProcessingException {

        // 아이디 통해 검색
        AccountEntity accountEntity = accountRepository.findAllByEmail(inpVo.getEmail());
        log.debug("AccountEntity is {}", accountEntity);

        // 조회되는 값이 없다면
        if (ObjectUtils.isEmpty(accountEntity)) {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_NO_ID);
        }

        // 비밀번호 유효성 검사
        boolean isPwVerify = CryptoUtils.isPwVerify(inpVo.getPassword(), accountEntity.getPassword());
        log.debug("isPwVerify = {}", isPwVerify);
        if (isPwVerify) {
            // Output Vo 작성
            String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getEmail()), accountEntity.getNickname());
            RedisDto redisDto = new RedisDto();
            redisDto.setEmail(accountEntity.getEmail());
            redisDto.setNickname(accountEntity.getNickname());
            redisDto.setAccessToken(token);
            redisDto.setAccountId(accountEntity.getAccountId());
            redisDto.setBzAccessToken(null);

            // 레디스에 세션 넣기
            redisUtils.putSession(token, redisDto);
            return redisDto;
        } else {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_INVALID_PW);
        }
    }

    /**
     * ToDo 여기 있어야 할게 아님.... 나중에 BLIZZARD 연동 계정 정보 가져오기 이런 기능을 만들면 옮겨야함
     * oAuth Credential_Code 방식을 이용해 AccessToken을 가져온다.
     *
     * @param code
     * @return
     */
    public String getAccessToken(String code) throws JsonProcessingException {
        //Auth Value발급을 위해 환경변수에서 clientId와 ClientSecret을 가져와 인코딩한다.
        String AUTH = CryptoUtils.getAuthValue();

        // RequestDto정의
        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .grant_type(GRANT_TYPE)
                .redirect_uri(REDIRECT_URI)
                .scope(SCOPE)
                .code(code)
                .build();

        // 토큰요청 ToDo 여기서 Exception이 난다면 어떻게 처리해야할까...
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
        log.debug("RequestBody is {}", body);
        Map<String, Object> checkTokenResult = battlenetClient.checkToken(body);

        // Feign 통신이 된다면
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
