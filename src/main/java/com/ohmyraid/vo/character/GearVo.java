package com.ohmyraid.vo.character;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GearVo implements Serializable {
    private static final long serialVersionUID = -2925893922100802694L;


    private int itemLevelEquipped;
    private int itemLevelTotal;
    private int artifact_traits;
}
