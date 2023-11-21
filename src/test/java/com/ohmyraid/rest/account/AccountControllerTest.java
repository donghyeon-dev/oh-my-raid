package com.ohmyraid.rest.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.account.ThreadInfDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    private static final Logger log = LoggerFactory.getLogger(AccountControllerTest.class);

    @Autowired
    MockMvc mockmvc;

    @Autowired
    RedisUtils redisUtils;


    @Test
    void updatePassword() throws JsonProcessingException {
        ThreadInfDto threadInfDto = new ThreadInfDto();
        // accessToken은 항상 수정해줘야함 => 뭔가 자동화를 시킬만한게 없을까?
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvaG15cmFpZCIsImlhdCI6MTY3MzkxNzA5NiwiTG9naW5JZCI6ImRvbmdoeWVvbmRldkBnbWFpbC5jb20iLCJVc2VyTmFtZSI6ImF1dG9jYXQiLCJleHAiOjE2NzM5NTMwOTZ9.SSpdTQoxVtECwfC9vVfV2zkCAEZg7GC8tyLfihUH0z0";
        threadInfDto.setAccessToken(token);
        ThreadLocalUtils.add(Constant.THREAD_INF, threadInfDto);
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setEmail("donghyeondev@gmail.com");
        userSessionDto.setAccessToken(token);
        redisUtils.storeObjectToRedis(token, userSessionDto);

    }

    @Test
    void updateNickname() {
    }
}