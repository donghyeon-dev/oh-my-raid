package com.ohmyraid.config;

import com.ohmyraid.dto.kafka.KafkaStoreData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = Constant.Kafka.TOPIC, groupId = Constant.Kafka.GROUP_ID)
    public void flightEventConsumer(KafkaStoreData kafkaStoreData) {
        log.info("Consumer consumed kafka message -> {}", kafkaStoreData.toString());
        log.info("Some business logic consum kafka messages");
    }
}
