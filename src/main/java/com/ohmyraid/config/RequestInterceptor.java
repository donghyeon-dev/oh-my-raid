package com.ohmyraid.config;

import com.ohmyraid.dto.account.ThreadInfDto;
import com.ohmyraid.utils.StringUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 프리핸들러에서 헤더의 Authorization(토큰)을 추출하여 쓰레드로컬에 넣는다.
        if (ThreadLocalUtils.isExist("token")) {
            log.debug("ThreadLocal is cleared");
            ThreadLocalUtils.clear();
        }
        String token = "";
        if (StringUtils.isNotEmpty(request.getHeader("Authorization"))) {
            token = request.getHeader("Authorization");
            ThreadInfDto threadInfDto = new ThreadInfDto();
            threadInfDto.setAccessToken(token);
            ThreadLocalUtils.add(Constant.THREAD_INF, threadInfDto);
            log.debug("Token is Exists. ThreadInf is added inside of ThreadLocal");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 포스트핸들러에서 쓰레드로컬 초기화
        ThreadLocalUtils.clear();

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
