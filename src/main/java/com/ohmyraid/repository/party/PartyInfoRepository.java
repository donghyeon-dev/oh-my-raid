package com.ohmyraid.repository.party;

import com.ohmyraid.domain.party.PartyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, Long>, PartyInfoRepositoryCustom {

}
