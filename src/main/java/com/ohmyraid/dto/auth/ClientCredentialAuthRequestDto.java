package com.ohmyraid.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ClientCredentialAuthRequestDto implements Serializable {

    private String grant_type;
}
