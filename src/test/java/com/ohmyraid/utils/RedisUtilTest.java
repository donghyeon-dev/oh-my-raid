package com.ohmyraid.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.dto.login.UserSessionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RedisUtilTest {

    private static final Logger log = LoggerFactory.getLogger(RedisUtilTest.class);


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void 레디스_커넥션_테스트() {
        String insertKeyValue = "key";
        String insertData = "data";

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(insertKeyValue, insertData);

        String result = valueOperations.get(insertKeyValue);
        log.info("result is {}", result);
        assertEquals(insertData, result);
    }

    @Test
    void ObjectMapper_레디스_커넥션_테스트() throws JsonProcessingException {
        String redisKey = "key";
        UserSessionDto redisInput = new UserSessionDto();
        redisInput.setNickname("autocat");
        redisInput.setEmail("donghyeondev@gmail.com");
        redisInput.setAccessToken("accessToken");

        String redisValue = objectMapper.writeValueAsString(redisInput);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        ;
        valueOperations.set(redisKey, redisValue);

        String selectedRedisSessionString = valueOperations.get(redisKey);

        log.info("result is {}", selectedRedisSessionString);
        assertEquals(redisValue, selectedRedisSessionString);
    }

    @Test
    void RedisUtils_레디스_커넥션_테스트() throws JsonProcessingException {
        String redisKey = "userInfo";
        UserSessionDto redisInput = new UserSessionDto();
        redisInput.setNickname("autocat");
        redisInput.setEmail("donghyeondev@gmail.com");
        redisInput.setAccessToken("accessToken");

        redisUtils.storeObjectToRedis(redisKey, redisInput);

        UserSessionDto redisSessionDto = redisUtils.getRedisValue(redisKey, UserSessionDto.class);
        log.info("result is {}", redisSessionDto);
        assertEquals(redisInput, redisSessionDto);
    }

}
