package com.ohmyraid.rest.login;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.service.login.LoginService;
import com.ohmyraid.vo.login.LoginInpVo;
import com.ohmyraid.vo.login.LoginOutpVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(value = "/")
    @ApiOperation(value = "로그인", notes = "이메일주소와 핸드폰번호로 로그인")
    public ResultView<LoginOutpVo> signIn(@RequestBody LoginInpVo loginInpVo){

        return new ResultView<>(loginService.signIn(loginInpVo));
    }
}
