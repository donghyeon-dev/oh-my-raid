package com.ohmyraid.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonNoAuthenticationException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.user.UserEntity;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.user.SignUpInpDto;
import com.ohmyraid.dto.user.SignUpResDto;
import com.ohmyraid.dto.user.UpdateNicknameDto;
import com.ohmyraid.dto.user.UpdatePasswordDto;
import com.ohmyraid.repository.user.UserRepository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtils redisUtils;

    public SignUpResDto signUp(SignUpInpDto signUpInpDto) {

        // 중복검사
        UserEntity dupplicateEmailEntity = userRepository.findAllByEmail(signUpInpDto.getEmail());
        UserEntity dupplicateNicknameEntity = userRepository.findAllByNickname(signUpInpDto.getNickname());
        if (!ObjectUtils.isEmpty(dupplicateEmailEntity)) { // EMAIL 중복검사
            throw new CommonServiceException(ErrorResult.DUP_EMAIL);
        }
        if (!ObjectUtils.isEmpty(dupplicateNicknameEntity)) { // NickName 중복검사
            throw new CommonServiceException(ErrorResult.DUP_NN);
        }

        userRepository.save(UserEntity.builder()
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

        UserEntity userEntity = userRepository.findAllByEmail(updatePasswordDto.getEmail());
        boolean isPwVerify = CryptoUtils.isPwVerify(updatePasswordDto.getCurrentPassword(), userEntity.getPassword());
        if (!isPwVerify) throw new CommonServiceException(ErrorResult.INVALID_PASSWORD);


        userEntity.updatePassword(CryptoUtils.encryptPw(updatePasswordDto.getChangePassword()));


        return userRepository.save(userEntity).getUserId();
    }

    public Long updateNickname(UpdateNicknameDto udpateNicknameRequest) throws JsonProcessingException {
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String email = redisUtils.getRedisValue(token, UserSessionDto.class).getEmail();
        if (!email.equals(udpateNicknameRequest.getEmail())) throw new CommonNoAuthenticationException();

        UserEntity userEntity = userRepository.findAllByEmail(udpateNicknameRequest.getEmail());
        userEntity.updateNickname(udpateNicknameRequest.getChangeNickname());

        return userRepository.save(userEntity).getUserId();

    }
}
