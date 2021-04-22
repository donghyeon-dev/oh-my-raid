package com.ohmyraid.service.login;

import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.JwtUtils;
import com.ohmyraid.vo.account.AccountVo;
import com.ohmyraid.vo.login.LoginInpVo;
import com.ohmyraid.vo.login.LoginOutpVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class LoginService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CharacterRespository characterRespository;

    @Autowired
    JwtUtils jwtUtils;

    public LoginOutpVo signIn(LoginInpVo inpVo){

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
            List<CharacterLoginOpMapping> characterList = characterRespository.findAllByAccountEntityAccountId(accountEntity.getAccountId());
            log.debug("CharacterList is {}", characterList);
            String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getEmail()), accountEntity.getNickname());
            LoginOutpVo outpVo = new LoginOutpVo();
            outpVo.setEmail(accountEntity.getEmail());
            outpVo.setNickname(accountEntity.getNickname());
            outpVo.setCharacterList(characterList);
            outpVo.setAccessToken(token);

            return outpVo;
        } else {
            throw new CommonServiceException(ErrorResult.LOGIN_FAIL_INVALID_PW);
        }
    }
}
