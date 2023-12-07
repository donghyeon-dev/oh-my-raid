package com.ohmyraid.feign;

import com.ohmyraid.config.RateLimited;
import com.ohmyraid.dto.wow_account.CharacterSpecInfoDto;
import com.ohmyraid.dto.wow_raid.RaidInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Wrapper class for the WowClient interface. Provides convenient methods to interact with the World of Warcraft API.
 * This class is annotated with @RateLimited to ensure that API calls are rate-limited.
 */
@RequiredArgsConstructor
@Component
public class WowClientWrapper {

    private final WowClient wowClient;

    /**
     * Retrieves the account profile summary.
     *
     * @param namespace The namespace for the profile. Default value: "profile-kr"
     * @param locale The locale for the profile. Default value: "ko_KR"
     * @param accessToken The access token for authentication
     * @param region The region for the profile. Default value: "kr"
     * @return A map containing the account profile summary
     */
    @RateLimited
    public Map<String,Object> getAccountProfileSummary(String namespace, String locale
            , String accessToken, String region){
        return wowClient.getAccountProfileSummary(namespace, locale, accessToken, region);
    };

    /**
     * Retrieves the character specialization information.
     *
     * @param namespace The namespace for the profile. Default value: "profile-kr"
     * @param accessToken The access token for authentication
     * @param locale The locale for the profile. Default value: "ko_KR"
     * @param slugEnglishName The slug/englishName for the character's profile URL
     * @param characterName The character's name
     * @return The character specialization information
     */
    @RateLimited
    public CharacterSpecInfoDto getCharacterSpec(String namespace, String accessToken, String locale, String slugEnglishName, String characterName) {
        return wowClient.getCharacterSpec(namespace, accessToken, locale, slugEnglishName, characterName);
    };

    /**
     * Retrieves the raid encounter information for a character.
     *
     * @param namespace The namespace for the profile. Default value: "profile-kr"
     * @param accessToken The access token for authentication
     * @param locale The locale for the profile. Default value: "ko_KR"
     * @param slugEnglishName The slug/englishName for the character's profile URL
     * @param characterName The character's name
     * @return The raid encounter information for the character
     */
    @RateLimited
    public RaidInfoDto getRaidEncounter(String namespace, String accessToken, String locale, String slugEnglishName, String characterName) {
        return wowClient.getRaidEncounter(namespace, accessToken, locale, slugEnglishName, characterName);
    };

}
