package com.ohmyraid.utils;

import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.user.ThreadInfDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThreadLocalTest {

    private static final Logger log = LoggerFactory.getLogger(RedisUtilTest.class);

    private static ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal();

    @Test
    public void ThreadLocal_add_test() {
        log.debug("ThreadLocal_add_test :: Started");

        Map<Object, Object> threadInfo = new HashMap<>();
        threadLocal.set(threadInfo);
        threadLocal.get().put("token", "abcdef");

        String token = threadLocal.get().get("token").toString();

        log.debug("Thread :: token is {}", token);
    }

    @Test
    public void ThreadLocal_CustomUtilTest() {
        ThreadInfDto threadInfDto = new ThreadInfDto();
        threadInfDto.setAccessToken("abcdef");
        ThreadLocalUtils.add(Constant.THREAD_INF, threadInfDto);

        log.debug("Thread's ThreadInfoVo is {}", ThreadLocalUtils.getThreadInfo());
    }

}
