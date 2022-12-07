package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class PlaybleClassConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        int value;
        switch (attribute) {
            case "전사":
                value = 1;
                break;
            case "성기사":
                value = 2;
                break;
            case "사냥꾼":
                value = 3;
                break;
            case "도적":
                value = 4;
                break;
            case "사제":
                value = 5;
                break;
            case "죽음의 기사":
                value = 6;
                break;
            case "주술사":
                value = 7;
                break;
            case "마법사":
                value = 8;
                break;
            case "흑마법사":
                value = 9;
                break;
            case "수도사":
                value = 10;
                break;
            case "드루이드":
                value = 11;
                break;
            case "악마사냥꾼":
                value = 12;
                break;
            case "기원사":
                value = 13;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attribute);
        }
        return value;
    }

    ;

    @Override
    public String convertToEntityAttribute(Integer dbdata) {
        String value = "";
        switch (dbdata) {
            case 1:
                value = "전사";
                break;
            case 2:
                value = "성기사";
                break;
            case 3:
                value = "사냥꾼";
                break;
            case 4:
                value = "도적";
                break;
            case 5:
                value = "사제";
                break;
            case 6:
                value = "죽음의 기사";
                break;
            case 7:
                value = "주술사";
                break;
            case 8:
                value = "마법사";
                break;
            case 9:
                value = "흑마법사";
                break;
            case 10:
                value = "수도사";
                break;
            case 11:
                value = "드루이드";
                break;
            case 12:
                value = "악마사냥꾼";
                break;
            case 13:
                value = "기원사";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        return value;
    }
}
