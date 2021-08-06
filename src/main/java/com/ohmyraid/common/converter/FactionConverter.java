package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class FactionConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {

        switch (attribute) {
            case "호드":
                attribute = "HORDE";
                break;
            case "얼라이언스":
                attribute = "ALLIANCE";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attribute);
        }
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "HORDE":
                dbData = "호드";
                break;
            case "ALLIANCE":
                dbData = "얼라이언스";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbData);
        }
        return dbData;
    }
}
