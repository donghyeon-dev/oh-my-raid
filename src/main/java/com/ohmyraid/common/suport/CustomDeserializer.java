package com.ohmyraid.common.suport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.dto.kafka.KafkaStoreData;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class CustomDeserializer implements Deserializer<KafkaStoreData<Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public KafkaStoreData<Object> deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        KafkaStoreData<Object> result;
        try {
            result = objectMapper.readValue(data, KafkaStoreData.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing KafkaStoreData", e);
        }

        return result;
    }
}