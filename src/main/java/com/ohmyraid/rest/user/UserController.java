package com.ohmyraid.rest.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.user.SignUpInpDto;
import com.ohmyraid.dto.user.SignUpResDto;
import com.ohmyraid.dto.user.UpdateNicknameDto;
import com.ohmyraid.dto.user.UpdatePasswordDto;
import com.ohmyraid.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "UserController", value = "OMR 유저 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "로그인이메일, 패스워드, 별명을 입력하여 계정을 등록한다.")
    @PostMapping(value = "/signup")
    public ResultView<SignUpResDto> signUp(
            @RequestBody SignUpInpDto signUpInpDto) {
        return new ResultView<>(userService.signUp(signUpInpDto));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "패스워드를 변경한다.")
    @PostMapping(value = "/update-password")
    public ResultView<Long> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto) throws JsonProcessingException {
        return new ResultView<>(userService.updatePassword(updatePasswordDto));
    }

    @ApiOperation(value = "닉네임 변경", notes = "닉네임을 변경한다.")
    @PostMapping(value = "/update-nickname")
    public ResultView<Long> updateNickname(
            @RequestBody UpdateNicknameDto udpateNicknameRequest) throws JsonProcessingException {
        return new ResultView<>(userService.updateNickname(udpateNicknameRequest));
    }
}
