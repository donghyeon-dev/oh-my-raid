package com.ohmyraid.repository.character;

import com.ohmyraid.domain.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRespository extends JpaRepository<CharacterEntity, Long> {
    public List<CharacterEntity> findAllByName(String name);
    public CharacterEntity findByName(String name);
    public CharacterEntity findByPlayableClass(String playableClass);
//    public List<CharacterEntity> findAllByAccountEntityAccountId(Long accountId);
//    public List<CharacterLoginOpMapping> findAllByAccountEntityAccountId(Long accountId);
}
