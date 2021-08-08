package com.ohmyraid.mapper.resolver;

import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.character.ActualCharacterDto;
import com.ohmyraid.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class CharacterResolver {

    private final AccountRepository accountRepository;

    @ObjectFactory
    public AccountEntity resolve(ActualCharacterDto dto, @TargetType Class<CharacterEntity> type) {
        return !ObjectUtils.isEmpty(dto) && !ObjectUtils.isEmpty(dto.getAccountId()) ?
                accountRepository.findByAccountId(dto.getAccountId()) :
                new AccountEntity();
    }

}
