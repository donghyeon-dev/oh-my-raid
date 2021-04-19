package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.CharacterRaidInfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRaidInfRespository extends JpaRepository<CharacterRaidInfEntity, Long> {
    public CharacterRaidInfEntity findByCharacterEntity_CharacterId(long characterId);
}
