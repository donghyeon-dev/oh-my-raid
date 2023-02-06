package com.ohmyraid.rest.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.RedisDto;
import com.ohmyraid.service.login.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "")
    @ApiOperation(value = "로그인", notes = "이메일주소와 비밀번호로 로그인")
    public ResultView<RedisDto> signIn(@RequestBody LoginInpDto loginInpDto) throws JsonProcessingException {

        return new ResultView<>(loginService.signIn(loginInpDto));
    }

    /**
     * ToDo 여기 있어야 할게 아님.... 나중에 BLIZZARD연동? 내 계정 정보 가져오기? 이런 기능을 만들면 옮겨야함 + Return도 수정해야함
     *
     * @param code
     * @return
     */
    
}
