package com.ohmyraid.config;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.naming.LimitExceededException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitAspect {

    private final Bucket tokenBucket;

    @Around("@annotation(com.ohmyraid.config.RateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable{
        if( !tokenBucket.tryConsume(1)){
            log.warn("Exceed limit of request to Blizzard API");
            // Some code put data to polling kafka queue.

            throw new LimitExceededException("API호출 횟수가 초과되었습니다.");
        }
        return joinPoint.proceed();
    }


}
