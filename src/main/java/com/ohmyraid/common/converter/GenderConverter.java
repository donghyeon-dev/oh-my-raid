package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        switch (attribute) {
            case "여성":
                attribute = "FEMALE";
                break;
            case "남성":
                attribute = "MALE";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attribute);

        }
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "FEMALE":
                dbData = "여성";
                break;
            case "MALE":
                dbData = "남성";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbData);

        }
        return dbData;
    }
}
