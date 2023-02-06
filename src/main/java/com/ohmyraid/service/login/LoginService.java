package com.ohmyraid.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.RedisDto;
import com.ohmyraid.repository.account.AccountRepository;
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

    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;


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


        boolean isPwVerify = CryptoUtils.isPwVerify(inpVo.getPassword(), accountEntity.getPassword());
        if (isPwVerify) {
            // Output Vo 작성
            String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getEmail()), accountEntity.getNickname());
            RedisDto redisDto = new RedisDto();
            redisDto.setEmail(accountEntity.getEmail());
            redisDto.setNickname(accountEntity.getNickname());
            redisDto.setAccessToken(token);
            redisDto.setAccountId(accountEntity.getAccountId());
            redisDto.setBzAccessToken(null);

            redisUtils.putSession(token, redisDto);
            return redisDto;
        } else {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_INVALID_PW);
        }
    }


}
