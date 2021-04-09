package com.ohmyraid.service.login;

import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.utils.JwtUtils;
import com.ohmyraid.vo.account.AccountVo;
import com.ohmyraid.vo.login.LoginInpVo;
import com.ohmyraid.vo.login.LoginOutpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtUtils jwtUtils;

    public LoginOutpVo signIn(LoginInpVo inpVo){

        // 아이디와 패스워드를 통해 검색
        AccountEntity accountEntity = accountRepository.findAllByEmailAndPassword(inpVo.getEmail(), inpVo.getPassword());

        // 조회되는 값이 없다면
        if(ObjectUtils.isEmpty(accountEntity)){
            throw new CommonServiceException(ErrorResult.INVALID_INPUT);
        }
        String token = jwtUtils.createAccessToken(String.valueOf(accountEntity.getAccountId()), accountEntity.getNickname());
        LoginOutpVo outpVo = new LoginOutpVo();
        outpVo.setEmail(accountEntity.getEmail());
        outpVo.setAccount(accountEntity);
        outpVo.setAccessToken(token);

        return outpVo;
    }
}
