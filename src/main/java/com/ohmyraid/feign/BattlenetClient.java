package com.ohmyraid.feign;

import com.ohmyraid.dto.auth.AuthDto;
import com.ohmyraid.dto.auth.AuthRequestDto;
import com.ohmyraid.dto.auth.CheckTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * Authorization_code를 사용해 Token을 받아오는 Feign Interface
 */
@FeignClient(name="OAU", url = "https://oauth.battle.net")
public interface BattlenetClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthDto getAccessToken(@RequestBody AuthRequestDto body,
                                       @RequestHeader(value = "Authorization") String authHeader);
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthDto getAccessTokenByClientCredential(@RequestHeader(value = "Authorization") String authHeader);

    @PostMapping(value = "/oauth/check_token" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> checkToken(@RequestBody CheckTokenDto body);
}
