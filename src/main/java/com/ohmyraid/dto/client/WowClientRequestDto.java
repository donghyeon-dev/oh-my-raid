package com.ohmyraid.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class WowClientRequestDto {

    @JsonProperty(value = "namespace", defaultValue = "profile-kr")
    private String namespace;

    @JsonProperty(value = "locale", defaultValue = "ko_KR")
    private String locale;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("slug")
    private String slugEnglishName;

    @JsonProperty("characterName")
    private String characterName;

    @JsonProperty(value = ":region", defaultValue = "kr")
    private String region;

}
