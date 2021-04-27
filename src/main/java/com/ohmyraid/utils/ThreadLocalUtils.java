package com.ohmyraid.utils;

import com.ohmyraid.config.Constant;
import com.ohmyraid.vo.account.ThreadInfVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ThreadLocalUtils {

    private static ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal();

    public static ThreadInfVo getThreadInfo(){
        return (ThreadInfVo) get(Constant.ThreadLocal.THREAD_INF);

    }

    public static void add(Object key, Object object) {
        log.debug("ThreadLocal add");
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
        log.debug("ThreadLocal clean");
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
