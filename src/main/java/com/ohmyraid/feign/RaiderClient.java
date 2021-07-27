package com.ohmyraid.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "RAD", url = "https://raider.io")
public interface RaiderClient {
    @GetMapping(value = "/api/v1/characters/profile")
    Map<String,Object> getCharacterGear(@RequestParam("region") String region,
                                       @RequestParam("realm") String realm,
                                       @RequestParam("name") String name,
                                       @RequestParam("fields") String field,
                                       @RequestHeader(value = "user-agent", defaultValue = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36") String ua
                                );

    @GetMapping(value = "/api/v1/characters/profile")
    Map<String,Object> getCharacterRaidProgress(@RequestParam("region") String region,
                                        @RequestParam("realm") String realm,
                                        @RequestParam("name") String name,
                                        @RequestParam("fields") String field,
                                        @RequestHeader(value = "user-agent", defaultValue = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36") String ua
    );

    @GetMapping(value = "/api/v1/characters/profile")
    Map<String,Object> getCharacterMythicScore(@RequestParam("region") String region,
                                                @RequestParam("realm") String realm,
                                                @RequestParam("name") String name,
                                                @RequestParam("fields") String field,
                                                @RequestHeader(value = "user-agent", defaultValue = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36") String ua
    );

}

