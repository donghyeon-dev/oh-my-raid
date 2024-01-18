package com.ohmyraid.feign;

import com.ohmyraid.dto.wow_account.AccountSummaryDto;
import com.ohmyraid.dto.wow_account.CharacterProfileSummary;
import com.ohmyraid.dto.wow_raid.RaidInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * World Of Warcraft profile-dynamic API Feign Interface
 */
@FeignClient(name = "WOW", url = "https://kr.api.blizzard.com", configuration = FeignConfig.class)
public interface WowClient {

    @GetMapping(value = "/profile/user/wow")
    AccountSummaryDto fetchAccountProfileSummary(@RequestParam(name = "namespace", defaultValue = "profile-kr") String namespace,
                                                 @RequestParam(name = "locale", defaultValue = "ko_KR") String locale,
                                                 @RequestParam(name = "access_token") String access_token,
                                                 @RequestParam(name = ":region", defaultValue = "kr") String region);

    @GetMapping(value = "profile/wow/character/{slug}/{characterName}")
    CharacterProfileSummary fetchCharacterProfileSummary(@RequestParam(name = "namespace", defaultValue = "profile-kr") String namespace,
                                                         @RequestParam(name = "access_token") String access_token,
                                                         @RequestParam(name = "locale", defaultValue = "ko_KR") String locale,
                                                         @PathVariable(name = "slug") String slugEnglishName,
                                                         @PathVariable(name = "characterName") String characterName
    );

    @GetMapping(value = "profile/wow/character/{slug}/{characterName}/encounters/raids")
    RaidInfoDto fetchRaidEncounter(@RequestParam(name = "namespace", defaultValue = "profile-kr") String namespace,
                                   @RequestParam(name = "access_token") String access_token,
                                   @RequestParam(name = "locale", defaultValue = "ko_KR") String locale,
                                   @PathVariable(name = "slug") String slugEnglishName,
                                   @PathVariable(name = "characterName") String characterName);
}
