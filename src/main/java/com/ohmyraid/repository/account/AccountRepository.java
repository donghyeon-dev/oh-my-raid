package com.ohmyraid.repository.account;

import com.ohmyraid.domain.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
