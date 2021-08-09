package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class ExpansionOptionCoverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        Integer value;
        switch (attribute) {
            case "키리안":
                value = 1;
                break;
            case "벤티르":
                value = 2;
                break;
            case "나이트 페이":
                value = 3;
                break;
            case "강령군주":
                value = 4;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attribute);
        }
        return value;
    }

    @Override
    public String convertToEntityAttribute(Integer dbData) {
        String value = "";
        switch (dbData) {
            case 1:
                value = "키리안";
                break;
            case 2:
                value = "벤티르";
                break;
            case 3:
                value = "나이트 페이";
                break;
            case 4:
                value = "강령군주";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        return value;
    }
}
