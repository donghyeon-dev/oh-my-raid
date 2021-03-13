package com.ohmyraid.rest.account;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.vo.account.SignUpInpVo;
import com.ohmyraid.vo.account.SignUpResVo;
import com.ohmyraid.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/signup")
    public ResultView<SignUpResVo> signUp(
            @RequestBody SignUpInpVo signUpInpVo){
        return new ResultView<>(accountService.signUp(signUpInpVo));
    }

    @GetMapping(value = "/test")
    public ResultView<String> test(){
        return new ResultView<>("hi");
    }

}
