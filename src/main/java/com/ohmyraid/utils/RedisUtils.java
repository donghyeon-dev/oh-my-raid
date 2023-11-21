package com.ohmyraid.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.config.Constant;
import com.ohmyraid.service.oauth.BlizzardTokenFetcher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class RedisUtils {

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final BlizzardTokenFetcher blizzardTokenFetcher;

    /**
     * 주어진 객체를 Redis에 저장하는 함수입니다. 객체 유형인 따라 처리 방식과 만료 시간이 달라집니다.
     *
     * <p>만약 value가 String 형식이면, 해당 값을 Redis에 그대로 저장하고, 만료 시간을 현재 시각으로부터 24시간 후로 설정합니다.
     * 그렇지 않은 경우(즉, value가 UserSessionDto 형식), value를 JSON 문자열 형식으로 변환하여 저장하고 만료 시간을 현재 시각으로부터 10시간 후로 설정합니다.
     *
     * <p>기본적으로, redisKey 또는 value가 null이거나 공백인 경우 IllegalArgumentException이 발생합니다.
     *
     * @param redisKey Redis에 저장할 값의 키
     * @param value    Redis에 저장할 값
     * @throws JsonProcessingException 값이 UserSessionDto라면 이를 JSON 문자열로 변환하는 과정에서 발생할 수 있는 예외
     */
    public void storeObjectToRedis(String redisKey, Object value) throws JsonProcessingException {
        if (!StringUtils.hasText(redisKey) || ObjectUtils.isEmpty(value)) {
            log.error("Either redisKey or value to be stored is absent.");
            throw new IllegalArgumentException("There is no parameter to execute storeSomethingToRedis()");
        }

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String redisValueString = "";
        Date expiredAt = new Date();
        if(value instanceof String){
            // 현재까진 blizzardAccessToken
            redisValueString = (String) value;
            expiredAt = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
        } else {
            // 현재까진 UserSessionDto
            redisValueString = objectMapper.writeValueAsString(value);
            expiredAt = Date.from(ZonedDateTime.now().plusHours(10).toInstant()); // Todo[Test를위한 만료기한 변경 (1hr to 10hr)]
        }
        valueOperations.set(redisKey, redisValueString);
        redisTemplate.expireAt(redisKey, expiredAt);
    }


    /**
     * 주어진 Redis 키에 연결된 값을 반환합니다. 사용하는 클래스 타입에 따라 반환 값이 다릅니다.
     *
     * <p>Redis에 키가 없는 경우, type이 String인 경우 새로운 BlizzardAccess 토큰을 가져옵니다.
     * 그외의 경우에는 null을 반환합니다.
     *
     * <p>키가 존재하고 type이 String인 경우, 해당 값이 String 형태로 반환됩니다.
     * 그외의 경우에는 ObjectMapper을 사용하여 JSON string을 원하는 객체 형태로 변환하여 반환합니다.
     *
     * @param redisKey Redis에서 가져올 값의 키
     * @param type     반환하려는 값의 클래스 타입 (예: String.class, UserSessionDto.class 등)
     * @return 반환하려는 값. type 매개변수에 지정된 클래스 형식에 따릅니다.
     * @throws JsonProcessingException ObjectMapper에서 JSON 문자열을 type 클래스 형식으로 변환하는 동안 발생할 수 있는 예외
     */
    public <T> T getRedisValue(String redisKey, Class<T> type)
            throws JsonProcessingException {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String valueString = valueOperations.get(redisKey);
        if(ObjectUtils.isEmpty(valueString)){
            log.warn("There is no value for key:{}.", redisKey);
            if(type == String.class){
                log.info("renew BlizzardAccesToken.");
                String renewedAccessToken = blizzardTokenFetcher.fetchBlizzardAccessToken();
                storeObjectToRedis(Constant.BLIZZARD_TOKEN, renewedAccessToken);
                return type.cast(renewedAccessToken);
            }
            return null;
        }

        if (type == String.class) {
            return type.cast(valueString);
        } else {
            return objectMapper.readValue(valueString, type);
        }
    }


}
