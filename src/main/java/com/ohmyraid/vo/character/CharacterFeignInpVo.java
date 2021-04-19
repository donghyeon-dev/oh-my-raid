package com.ohmyraid.vo.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterFeignInpVo {

    String region;
    String realm;
    String name;
}
