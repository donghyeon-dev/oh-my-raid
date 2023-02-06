package com.ohmyraid.rest.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.auth.StoreAtReqDto;
import com.ohmyraid.service.oauth.OauthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @GetMapping(value = "/oauth")
    @ApiOperation(value = "Blizzard oauth 인증", notes = "oAuth Credential Code를 통해 AccessToken 발급")
    public ResultView<String> oauth(@RequestParam(value = "code") String code) throws JsonProcessingException {
        log.debug("Code is {}", code);
        return new ResultView<>(oauthService.getAccessToken(code));
    }

    @PostMapping(value = "/oauth/storeAccessToken")
    @ApiOperation(value = "BlizzardAccessToken 저장", notes = "AccessToken을 해당 세션에 저장한다.")
    public ResultView<Boolean> storeAccessToken(@RequestBody StoreAtReqDto reqDto) throws JsonProcessingException {
        return new ResultView<>(oauthService.storeAccessToken(reqDto));
    }

}
