package com.ohmyraid.utils;

import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.account.ThreadInfDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ThreadLocalUtils {

    private static ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal();

    public static ThreadInfDto getThreadInfo() {
        return (ThreadInfDto) get(Constant.THREAD_INF);

    }

    public static void add(Object key, Object object) {
        getAll().put(key, object);
    }

    public static Object get(Object key) {
        return getAll().get(key);
    }

    public static boolean isExist(Object key) {
        Object object = getAll().get(key);
        return object != null;
    }

    public static void clear() {
        // remove()를 통해 제거하지 않는다면 스레드 풀을 재사용하여 문제가 발생할 수 있음
        threadLocal.remove();
    }

    private static Map<Object, Object> getAll() {
        HashMap<Object, Object> sharedInfo;
        if (threadLocal.get() == null) {
            sharedInfo = new HashMap<Object, Object>();
            threadLocal.set(sharedInfo);
        }
        return (Map<Object, Object>) threadLocal.get();
    }


}
