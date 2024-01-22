package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.dto.wow_account.CharacterDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long>, CharacterRepositoryCustom {

    public CharacterEntity findByName(String name);

    public CharacterEntity findCharacterByCharacterSeNumber(int seNumber);

    public CharacterEntity findCharacterEntityByCharacterId(long characterId);

    public List<CharacterEntity> findAllByUserEntity_UserIdOrderByEquippedItemLevelDesc(long userId);

    public List<CharacterDto> findCharacterDtosByUserId(long userId);

    public CharacterDto findCharacterDtoBySlugAndName(CharacterSpecRequest request);
}
