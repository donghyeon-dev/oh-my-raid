package com.ohmyraid.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestDtoTest {

    @Test
    void JsonNaming애노테이션_테스트() throws JsonProcessingException {

        Map<String, Object> map = new HashMap<>();

        map.put("class_name", "className");
        map.put("id" ,1);

        ObjectMapper mapper = new ObjectMapper();
        TestDto dto = mapper.convertValue(map, TestDto.class);

        assertEquals(dto.getClassName(),"className");


    }

}