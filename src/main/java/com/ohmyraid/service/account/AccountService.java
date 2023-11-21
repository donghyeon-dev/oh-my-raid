package com.ohmyraid.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonNoAuthenticationException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.dto.account.SignUpInpDto;
import com.ohmyraid.dto.account.SignUpResDto;
import com.ohmyraid.dto.account.UpdatePasswordDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RedisUtils redisUtils;

    public SignUpResDto signUp(SignUpInpDto signUpInpDto) {

        // 중복검사
        AccountEntity dupplicateEmailEntity = accountRepository.findAllByEmail(signUpInpDto.getEmail());
        AccountEntity dupplicateNicknameEntity = accountRepository.findAllByNickname(signUpInpDto.getNickname());
        if ( !ObjectUtils.isEmpty(dupplicateEmailEntity)) { // EMAIL 중복검사
            throw new CommonServiceException(ErrorResult.DUP_EMAIL);
        }
        if ( !ObjectUtils.isEmpty(dupplicateNicknameEntity)) { // NickName 중복검사
            throw new CommonServiceException(ErrorResult.DUP_NN);
        }

        accountRepository.save(AccountEntity.builder()
                .email(signUpInpDto.getEmail())
                .password(CryptoUtils.encryptPw(signUpInpDto.getPassword()))
                .nickname(signUpInpDto.getNickname())
                .build());
        SignUpResDto signUpResDto = new SignUpResDto();
        signUpResDto.setMessage("회원가입 성공");
        return signUpResDto;
    }

    public Long updatePassword(UpdatePasswordDto updatePasswordDto) throws JsonProcessingException {
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String email = redisUtils.getRedisValue(token, UserSessionDto.class).getEmail();
        if (!email.equals(updatePasswordDto.getEmail())) throw new CommonNoAuthenticationException();

        AccountEntity accountEntity = accountRepository.findAllByEmail(updatePasswordDto.getEmail());
        boolean isPwVerify = CryptoUtils.isPwVerify(updatePasswordDto.getCurrentPassword(), accountEntity.getPassword());
        if (!isPwVerify) throw new CommonServiceException(ErrorResult.INVALID_PASSWORD);


        accountEntity.updatePassword(CryptoUtils.encryptPw(updatePasswordDto.getChangePassword()));


        return accountRepository.save(accountEntity).getAccountId();
    }

    public Long updateNickname(SignUpInpDto inpDto) throws JsonProcessingException {
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String email = redisUtils.getRedisValue(token, UserSessionDto.class).getEmail();
        if (!email.equals(inpDto.getEmail())) throw new CommonNoAuthenticationException();

        AccountEntity accountEntity = accountRepository.findAllByEmail(inpDto.getEmail());
        accountEntity.updateNickname(inpDto.getNickname());

        return accountRepository.save(accountEntity).getAccountId();

    }
}
