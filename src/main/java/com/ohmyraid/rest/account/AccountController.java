package com.ohmyraid.rest.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.account.SignUpInpDto;
import com.ohmyraid.dto.account.SignUpResDto;
import com.ohmyraid.service.account.AccountService;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Api(tags = "AccountController")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final CharacterService characterService;

    @ApiOperation(value = "회원가입", notes = "로그인이메일, 패스워드, 별명을 입력하여 계정을 등록한다.")
    @PostMapping(value = "/signup")
    public ResultView<SignUpResDto> signUp(
            @RequestBody SignUpInpDto signUpInpDto) {
        return new ResultView<>(accountService.signUp(signUpInpDto));
    }

    @GetMapping(value = "{accountId}/sync")
    @ApiOperation(value = "/profile/user/wow?", notes = "계정의 캐릭터 정보를 동기화한다.")
    public ResultView<Boolean> getTotalSummary(@PathVariable long accountId) throws JsonProcessingException, InterruptedException {
        return new ResultView<>(characterService.getTotalSummary(accountId));
    }

}
