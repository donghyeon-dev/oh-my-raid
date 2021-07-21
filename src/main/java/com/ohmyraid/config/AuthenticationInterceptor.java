package com.ohmyraid.config;

import com.ohmyraid.common.result.CommonNoAuthenticationException;
import com.ohmyraid.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================  REQUEST RECEIVED  ===============================\n" +
                ">>>>>>>>>>  Request URI \t : {}  <<<<<<<<<<", request.getRequestURI());
        // Header의 AccessToken의 유효성 검사
        String header =  request.getHeader("Authorization");
        log.debug("AuthenticationInterceptor :: Authorization Value is {}", header);
        if(!ObjectUtils.isEmpty(header)) {
            boolean isTokenValid = jwtUtils.isTokenValid(header);
            if (!isTokenValid) {
                log.error("AuthenticationInterceptor :: AccessToken is INVALID");
                throw new CommonNoAuthenticationException();
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug(">>>>>>>>>>>  Request URI \t : {}  <<<<<<<<<<<  \n" +
                "===============================  REQUEST DONE  ===============================", request.getRequestURI());
    }

}
