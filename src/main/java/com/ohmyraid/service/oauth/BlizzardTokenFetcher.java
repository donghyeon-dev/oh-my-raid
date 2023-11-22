package com.ohmyraid.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.dto.auth.AuthResponseDto;
import com.ohmyraid.dto.auth.ClientCredentialAuthRequestDto;
import com.ohmyraid.feign.BattlenetClient;
import com.ohmyraid.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class BlizzardTokenFetcher {

    private final BattlenetClient battlenetClient;

    @Value("${bz.normal-grant-type}")
    private String GRANT_TYPE;


}
