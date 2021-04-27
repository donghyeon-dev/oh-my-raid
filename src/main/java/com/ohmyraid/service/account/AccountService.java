package com.ohmyraid.service.account;

import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.vo.account.SignUpInpVo;
import com.ohmyraid.vo.account.SignUpResVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public SignUpResVo signUp(SignUpInpVo signUpInpVo){

        // 중복검사
        List<AccountEntity> accountList = accountRepository.findAll();
        List<AccountEntity> nameList = accountRepository.findAll();
        accountList = accountList.stream()
                .filter(a -> a.getEmail().equals(signUpInpVo.getEmail()))
                .collect(Collectors.toList());
        nameList = accountList.stream()
                .filter(a -> a.getNickname().equals(signUpInpVo.getNickname()))
                .collect(Collectors.toList());
        if(accountList.size() != 0 ){ // ID 중복검사
            throw new CommonServiceException(ErrorResult.DUP_ID);
        } else if(nameList.size() != 0 ){ // NickName 중복검사
            throw new CommonServiceException(ErrorResult.DUP_NN);
        } else{
            accountRepository.save(AccountEntity.builder()
                    .email(signUpInpVo.getEmail())
                    .password(CryptoUtils.encryptPw(signUpInpVo.getPassword()))
                    .nickname(signUpInpVo.getNickname())
                    .build());
            SignUpResVo signUpResVo = new SignUpResVo();
            signUpResVo.setMessage("회원가입 성공");
            return signUpResVo;
        }
    }
}
