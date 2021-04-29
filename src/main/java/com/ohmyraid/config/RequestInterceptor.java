package com.ohmyraid.config;

import com.ohmyraid.utils.StringUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import com.ohmyraid.dto.account.ThreadInfDto;
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

        if(ThreadLocalUtils.isExist("token")){
            log.debug("ThreadLocal is cleared");
            ThreadLocalUtils.clear();
        }
        String token = "";
        if(StringUtils.isNotEmpty(request.getHeader("Authorization"))) {
            token = request.getHeader("Authorization");
            ThreadInfDto threadInfDto = new ThreadInfDto();
            threadInfDto.setAccessToken(token);
            ThreadLocalUtils.add("ThreadInf", threadInfDto);
            log.debug("Token is Exists. ThreadInf is added inside of ThreadLocal");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadLocalUtils.clear();

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
