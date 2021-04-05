package com.ohmyraid.feign;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.vo.character.CharacterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "RAD", url = "https://raider.io")
public interface RaiderClient {
    @GetMapping(value = "/api/v1/characters/profile")
    Map<String,String> getCharacterInf(@RequestParam("region") String region,
                                       @RequestParam("realm") String realm,
                                       @RequestParam("name") String name,
                                       @RequestParam("fields") String field,
                                       @RequestHeader(value = "user-agent", defaultValue = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36") String ua
                                );


}

