package com.ohmyraid.repository.character;

import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.dto.wow_account.CharacterDto;

import java.util.List;

public interface CharacterRepositoryCustom {

    public List<CharacterDto> findCharacterDtosByUserId(long accountId);

    public CharacterDto findCharacterDtoBySlugAndName(CharacterSpecRequest request);
}
