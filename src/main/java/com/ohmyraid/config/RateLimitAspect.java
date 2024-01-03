package com.ohmyraid.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.common.suport.CustomSerializer;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.kafka.KafkaStoreData;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.naming.LimitExceededException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class RateLimitAspect {

    private final Bucket tokenBucket;

    private final KafkaProducer kafkaProducer;


    @Around("@annotation(com.ohmyraid.config.RateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable{
        if( !tokenBucket.tryConsume(1)){
            log.warn("Exceed limit of request to Blizzard API");
            kafkaProducer.sendFlightEvent(KafkaStoreData.builder()
                            .methodName(joinPoint.getSignature().getName())
                            .targetParameter(joinPoint.getArgs()[0])
                            .parameterTargetClass(WowClientRequestDto.class)
                    .build());
            throw new LimitExceededException("API호출 횟수가 초과되었습니다.");
        }
        return joinPoint.proceed();
    }


}
