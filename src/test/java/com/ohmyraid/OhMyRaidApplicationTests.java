package com.ohmyraid;

import com.ohmyraid.config.KafkaConsumer;
import com.ohmyraid.config.KafkaProducer;
import com.ohmyraid.dto.user.UserDto;
import com.ohmyraid.dto.kafka.KafkaStoreData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class OhMyRaidApplicationTests {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Test
    void Kafka_통신_테스트(){
        KafkaStoreData<Object> message = KafkaStoreData.builder()
                .methodName("test")
                .targetParameter(UserDto.builder().userId(1L).email("donghyeon").build())
                .parameterTargetClass(UserDto.class)
                .build();
        kafkaProducer.sendFlightEvent(message);


    }

}
