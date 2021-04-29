package com.ohmyraid.service.account;

import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.dto.account.SignUpInpDto;
import com.ohmyraid.dto.account.SignUpResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public SignUpResDto signUp(SignUpInpDto signUpInpDto){

        // 중복검사
        List<AccountEntity> accountList = accountRepository.findAll();
        List<AccountEntity> nameList = accountRepository.findAll();
        accountList = accountList.stream()
                .filter(a -> a.getEmail().equals(signUpInpDto.getEmail()))
                .collect(Collectors.toList());
        nameList = accountList.stream()
                .filter(a -> a.getNickname().equals(signUpInpDto.getNickname()))
                .collect(Collectors.toList());
        if(accountList.size() != 0 ){ // ID 중복검사
            throw new CommonServiceException(ErrorResult.DUP_ID);
        } else if(nameList.size() != 0 ){ // NickName 중복검사
            throw new CommonServiceException(ErrorResult.DUP_NN);
        } else{
            accountRepository.save(AccountEntity.builder()
                    .email(signUpInpDto.getEmail())
                    .password(CryptoUtils.encryptPw(signUpInpDto.getPassword()))
                    .nickname(signUpInpDto.getNickname())
                    .build());
            SignUpResDto signUpResDto = new SignUpResDto();
            signUpResDto.setMessage("회원가입 성공");
            return signUpResDto;
        }
    }
}
