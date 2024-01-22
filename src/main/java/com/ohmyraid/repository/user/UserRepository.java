package com.ohmyraid.repository.user;

import com.ohmyraid.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findAllByEmail(String email);

    public UserEntity findAllByNickname(String nickname);

    public UserEntity findByUserId(long id);
}
