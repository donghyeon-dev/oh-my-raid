package com.ohmyraid.mapper;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.dto.character.ActualCharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper//(uses = {CharacterResolver.class})
public interface CharacterMapper {

//    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);


    //    @Mapping(source = "accountId", target = "accountEntity")
//    CharacterEntity characterDtoToEntity(ActualCharacterDto characterDto, @Context AccountRepository repository);

    @Mapping(target = "accountId", source = "characterEntity.accountEntity.accountId")
    @Mapping(target = "realm", ignore = true)
    ActualCharacterDto characterEntityToDto(CharacterEntity characterEntity);

//    @ObjectFactory
//    default AccountEntity findByAccountId(ActualCharacterDto dto, @TargetType Class<AccountEntity> type,
//                                          @Context AccountRepository repo) {
//        return !ObjectUtils.isEmpty(dto) && !ObjectUtils.isEmpty(dto.getAccountId())
//                ? repo.findByAccountId(dto.getAccountId())
//                : new AccountEntity();
//    }

}
