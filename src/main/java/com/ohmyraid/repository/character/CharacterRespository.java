package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRespository extends JpaRepository<CharacterEntity, Long> {
}
