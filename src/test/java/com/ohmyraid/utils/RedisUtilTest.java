package com.ohmyraid.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.dto.login.RedisDto;
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

// junit5에서 Assertion으로 변경됬다고함!!
import static org.junit.jupiter.api.Assertions.*;

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
    void 레디스_커넥션_테스트(){
        String key = "key";
        String data = "data";

        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();;
        valueOperations.set(key,data);

        String result = valueOperations.get(key);
        log.info("result is {}", result);
        assertEquals(data,result);
    }

    @Test
    void ObjectMapper_레디스_커넥션_테스트() throws JsonProcessingException {
        String key = "key";
        RedisDto outpVo = new RedisDto();
        outpVo.setNickname("autocat");
        outpVo.setEmail("donghyeondev@gmail.com");
        outpVo.setAccessToken("accessToken");

        String value = objectMapper.writeValueAsString(outpVo);

        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();;
        valueOperations.set(key,value);

        String result = valueOperations.get(key);
        log.info("result is {}", result);
        assertEquals(value,result);
    }

    @Test
    void RedisUtils_레디스_커넥션_테스트() throws JsonProcessingException {
        String key = "key";
        RedisDto outpVo = new RedisDto();
        outpVo.setNickname("autocat");
        outpVo.setEmail("donghyeondev@gmail.com");
        outpVo.setAccessToken("accessToken");

        redisUtils.putSession(key,outpVo);

        RedisDto result = redisUtils.getSession(key);
        log.info("result is {}", result);
        assertEquals(outpVo,result);
    }

}
