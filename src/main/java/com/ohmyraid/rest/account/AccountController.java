package com.ohmyraid.rest.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.account.SignUpInpDto;
import com.ohmyraid.dto.account.SignUpResDto;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import com.ohmyraid.service.account.AccountService;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "계정의 전체 캐릭터정보 동기화", notes = "해당 계정의 캐릭터 정보를 동기화한다.")
    public ResultView<Boolean> getTotalSummary(@PathVariable long accountId) throws JsonProcessingException, InterruptedException {
        return new ResultView<>(characterService.getTotalSummary(accountId));
    }

    @GetMapping(value = "/{accountId}/characters")
    @ApiOperation(value = "계정의 전체 캐릭터 간략보기", notes = "내 계정의 모든 캐릭터의 정보를 조회한다.")
    public ResultView<List<ActualCharacterDto>> getMyCharacter(@PathVariable long accountId) throws JsonProcessingException {
        return new ResultView<List<ActualCharacterDto>>(characterService.getMyCharacter(accountId));
    }

    @GetMapping("/{accountId}/raid-encounter")
    @ApiOperation(value = "캐릭터의 레이드 정보 동기화", notes = "사용자 계정에 저장된 캐릭터들의 레이드들의 정보를 동기화한다.")
    public ResultView<Boolean> getRaidEncounter(@PathVariable long accountId) throws Exception {
        return new ResultView<>(characterService.getRaidEncounter(accountId));
    }

}
