package com.ohmyraid.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.JwtUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.LoginOutpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;

    private final CharacterRespository characterRespository;

    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;

    /**
     * 로그인 서비스
     * @param inpVo
     * @return
     * @throws JsonProcessingException
     */
    public LoginOutpDto signIn(LoginInpDto inpVo) throws JsonProcessingException {

        // 아이디 통해 검색
        AccountEntity accountEntity = accountRepository.findAllByEmail(inpVo.getEmail());
        log.debug("AccountEntity is {}", accountEntity);

        // 조회되는 값이 없다면
        if(ObjectUtils.isEmpty(accountEntity)){
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_NO_ID);
        }

        // 비밀번호 유효성 검사
        boolean isPwVerify = CryptoUtils.isPwVerify(inpVo.getPassword(), accountEntity.getPassword());
        log.debug("isPwVerify = {}",isPwVerify);
        if(isPwVerify) {
            // Output Vo 작성
            List<CharacterLoginOpMapping> characterList = characterRespository.findAllByAccountEntityAccountId(accountEntity.getAccountId());
            log.debug("CharacterList is {}", characterList);
            String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getEmail()), accountEntity.getNickname());
            LoginOutpDto outpVo = new LoginOutpDto();
            outpVo.setEmail(accountEntity.getEmail());
            outpVo.setNickname(accountEntity.getNickname());
            outpVo.setCharacterList(characterList);
            outpVo.setAccessToken(token);

            // 레디스에 세션 넣기
            redisUtils.putSession(token, outpVo);
            return outpVo;
        } else {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_INVALID_PW);
        }
    }
}
