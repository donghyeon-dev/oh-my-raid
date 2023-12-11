package com.ohmyraid.config;

import com.ohmyraid.dto.kafka.KafkaStoreData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate kafkaTemplate;

    public void sendFlightEvent(KafkaStoreData kafkaStoreData) {
        log.info("Producer produced the Data -> {}", kafkaStoreData.toString());
        kafkaTemplate.send(Constant.Kafka.TOPIC, kafkaStoreData);
    }
}
