package com.ohmyraid.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 namespace", example = "profile-kr")
    private String namespace;

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 locale", example = "ko_KR")
    private String locale;

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 access_token")
    private String accessToken;

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 region", example = "azshara")
    private String slugEnglishName;

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 characterName", example = "박동현")
    private String characterName;

    @ApiModelProperty(notes = "블리자드 API에서 사용하는 region", example = "kr")
    private String region;

    @ApiModelProperty(notes = "OMR 내 캐릭터 고유 번호", example = "123456789")
    private Long characterId;

    @ApiModelProperty(notes = "OMR 내 계정 고유 번호", example = "123456789")
    private Long accountId;

}
