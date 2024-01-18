package com.ohmyraid.feign;

import com.ohmyraid.dto.auth.AuthResponseDto;
import com.ohmyraid.dto.auth.AuthorizationCodeAuthRequestDto;
import com.ohmyraid.dto.auth.ClientCredentialAuthRequestDto;
import com.ohmyraid.dto.auth.CheckTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * Authorization_code를 사용해 Token을 받아오는 Feign Interface
 */
@FeignClient(name="OAU", url = "https://oauth.battle.net", configuration = FeignConfig.class)
public interface BattlenetClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthResponseDto fetchAccessTokenByAuthorizationCode(@RequestBody AuthorizationCodeAuthRequestDto body,
                                                        @RequestHeader(value = "Authorization") String authHeader);
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthResponseDto fetchAccessTokenByClientCredential(@RequestHeader(value = "Authorization") String authHeader,
                                                       @RequestBody ClientCredentialAuthRequestDto authDto);

    @PostMapping(value = "/oauth/check_token" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> checkToken(@RequestBody CheckTokenDto body);
}
