package com.ohmyraid.dto;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Value;

@Data
@JsonIgnoreProperties
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestDto {

    int id;
    String className;
}
