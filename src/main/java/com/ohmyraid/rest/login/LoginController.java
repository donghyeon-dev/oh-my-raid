package com.ohmyraid.rest.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.service.login.LoginService;
import com.ohmyraid.dto.login.LoginInpDto;
import com.ohmyraid.dto.login.LoginOutpDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "")
    @ApiOperation(value = "로그인", notes = "이메일주소와 비밀번호로 로그인")
    public ResultView<LoginOutpDto> signIn(@RequestBody LoginInpDto loginInpDto) throws JsonProcessingException {

        return new ResultView<>(loginService.signIn(loginInpDto));
    }
    @GetMapping(value = "/oauth")
    @ApiOperation(value = "oauth", notes = "이메일주소와 비밀번호로 로그인")
    public ResultView<String> oauth(@RequestParam(value = "code") String code){
        log.debug("Code is {}", code);
        return new ResultView<String>("hi");
    }
}
