package com.ohmyraid.feign;

import com.ohmyraid.config.RateLimited;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.wow_account.CharacterSpecInfoDto;
import com.ohmyraid.dto.wow_raid.RaidInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Wrapper class for the WowClient interface. Provides convenient methods to interact with the World of Warcraft API.
 * This class's methods are annotated with @RateLimited to ensure that API calls are rate-limited.
 */
@RequiredArgsConstructor
@Component
public class WowClientWrapper {

    private final WowClient wowClient;

    /**
     * Retrieves the account profile summary for a user in World of Warcraft.
     *
     * @param requestDto the request object containing the necessary parameters
     * @return a map containing the account profile summary information
     */
    @RateLimited
    public Map<String, Object> getAccountProfileSummary(WowClientRequestDto requestDto) {
        return wowClient.getAccountProfileSummary(requestDto.getNamespace(),
                requestDto.getLocale(),
                requestDto.getAccessToken(),
                requestDto.getRegion());
    }

    ;

    /**
     * Retrieves the character specialization information for a character in World of Warcraft.
     *
     * @param requestDto the request object containing the necessary parameters
     * @return the CharacterSpecInfoDto object representing the character specialization information
     */
    @RateLimited
    public CharacterSpecInfoDto getCharacterSpec(WowClientRequestDto requestDto){
        return wowClient.getCharacterSpec(requestDto.getNamespace(),
                requestDto.getAccessToken(),
                requestDto.getLocale(),
                requestDto.getSlugEnglishName(),
                requestDto.getCharacterName());
    }

    ;

    /**
     * Retrieves the raid encounter information for a character in World of Warcraft.
     *
     * @param requestDto the request object containing the necessary parameters
     * @return the RaidInfoDto object representing the raid encounter information
     */
    @RateLimited
    public RaidInfoDto getRaidEncounter(WowClientRequestDto requestDto) {
        return wowClient.getRaidEncounter(requestDto.getNamespace(),
                requestDto.getAccessToken(),
                requestDto.getLocale(),
                requestDto.getSlugEnglishName(),
                requestDto.getCharacterName());
    }

    ;

}
