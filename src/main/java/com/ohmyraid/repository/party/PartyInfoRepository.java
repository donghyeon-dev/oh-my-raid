package com.ohmyraid.repository.party;

import com.ohmyraid.domain.party.PartyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, Long>, PartyInfoRepositoryCustom {

    public List<PartyInfoEntity> findPartyInfoEntitiesByCreateAccountId_AccountId(long createAccountId);

    public PartyInfoEntity findPartyInfoEntityByPartyId(long partyId);

}
