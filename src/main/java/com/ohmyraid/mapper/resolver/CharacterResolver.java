package com.ohmyraid.mapper.resolver;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.character.ActualCharacterDto;
import com.ohmyraid.repository.account.AccountRepository;
import org.mapstruct.Context;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class CharacterResolver {


    @Autowired
    AccountRepository accountRepository;

    public CharacterEntity findByAccountId(ActualCharacterDto dto, @TargetType Class<CharacterEntity> type,
                                           @Context AccountRepository repo) {
        return !ObjectUtils.isEmpty(dto) && !ObjectUtils.isEmpty(dto.getAccountId())
                ? CharacterEntity.builder().accountEntity(repo.findByAccountId(dto.getAccountId())).build()
                : new CharacterEntity();
    }

}
