package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRespository extends JpaRepository<CharacterEntity, Long>, CharacterRepositoryCustom {

    public CharacterEntity findByName(String name);

    public CharacterEntity findCharacterByCharacterSeNumber(int seNumber);

    public CharacterEntity findCharacterEntityByCharacterId(long characterId);

    public List<CharacterEntity> findAllByAccountEntity_AccountIdOrderByEquippedItemLevelDesc(long accountId);
}
