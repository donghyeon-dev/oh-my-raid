package com.ohmyraid.dto.wow_account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterSpecInfoDto {

    public int id;
    public String name;
    public ActiveSpec activeSpec;
    public int level;
    public long lastLoginTimestamp;
    public int averageItemLevel;
    public int equippedItemLevel;
    public CovenantProgress covenantProgress;
}
