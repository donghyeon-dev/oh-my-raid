package com.ohmyraid.config;

import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfig {

    @Bean
    public Bucket tokenBucket(){
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(36000).refillIntervally(36000, Duration.ofHours(1)))
                .addLimit(limit -> limit.capacity(100).refillIntervally(100, Duration.ofMinutes(1)))
//                 Todo : For test
//                .addLimit(limit -> limit.capacity(3).refillIntervally(3, Duration.ofMinutes(1)))
                .build();

    }

}
