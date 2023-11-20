package com.ohmyraid.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.RedisDto;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.service.oauth.OauthService;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.JwtUtils;
import com.ohmyraid.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;

    private final OauthService oauthService;

    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;


    /**
     * 사용자 로그인을 담당하는 메소드입니다.
     * 이메일을 통해 해당 계정을 조회하고, 비밀번호를 검증한 뒤 토큰을 생성하여 반환합니다.
     *
     * @param inpVo 로그인 정보를 담고 있는 DTO. 이메일과 비밀번호를 포함합니다.
     * @return 로그인 성공 시 생성된 토큰 정보와 함께 사용자 정보를 담고 있는 RedisDto를 반환합니다.
     * @throws JsonProcessingException 토큰 생성 시 발생할 수 있는 예외입니다.
     * @throws CommonServiceException  이메일이 데이타베이스에 존재하지 않거나 비밀번호가 일치하지 않는 경우 발생하는 예외입니다.
     */
    public RedisDto signIn(LoginInpDto inpVo) throws JsonProcessingException {

        // 아이디 통해 검색
        AccountEntity accountEntity = accountRepository.findAllByEmail(inpVo.getEmail());
        log.debug("AccountEntity is {}", accountEntity);

        // 조회되는 값이 없다면
        if (ObjectUtils.isEmpty(accountEntity)) {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_NO_ID);
        }


        if ( CryptoUtils.isPwVerify(inpVo.getPassword(), accountEntity.getPassword()) ) {
            // Output Vo 작성
            String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getEmail()), accountEntity.getNickname());
            RedisDto redisDto = new RedisDto();
            redisDto.setEmail(accountEntity.getEmail());
            redisDto.setNickname(accountEntity.getNickname());
            redisDto.setAccessToken(token);
            redisDto.setAccountId(accountEntity.getAccountId());
            redisDto.setBzAccessToken(oauthService.getAccessTokenWithClientCredential());

            redisUtils.putSession(token, redisDto);
            return redisDto;
        } else {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_INVALID_PW);
        }
    }


}
