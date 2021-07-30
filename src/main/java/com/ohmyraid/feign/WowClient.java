package com.ohmyraid.feign;

import com.ohmyraid.dto.auth.AuthDto;
import com.ohmyraid.dto.auth.AuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * World Of Warcraft API Feign Interface
 */
@FeignClient(name="WOW", url = "https://kr.api.blizzard.com")
public interface WowClient {

    @GetMapping(value = "/profile/user/wow")
    Map<String,Object> getAccountProfileSummary(@RequestParam(name = "namespace" , defaultValue = "profile-kr") String namespace,
                                                @RequestParam(name = "locale", defaultValue = "ko_KR") String locale,
                                                @RequestParam(name = "access_token") String access_token,
                                                @RequestParam(name = ":region", defaultValue = "kr") String region);




}
