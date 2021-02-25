package com.ohmyraid.rest.account;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.vo.account.SignUpInpVo;
import com.ohmyraid.vo.account.SignUpResVo;
import com.ohmyraid.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/signup")
    public ResultView<SignUpResVo> signUp(@RequestBody SignUpInpVo signUpInpVo){
        return new ResultView<>(accountService.signUp(signUpInpVo));
    }
}
