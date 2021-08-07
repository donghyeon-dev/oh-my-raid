package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class SlugConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        int value;
        switch (attribute) {
            case "불타는 군단":
                value = 201;
                break;
            case "아즈샤라":
                value = 205;
                break;
            case "달라란":
                value = 207;
                break;
            case "듀로탄":
                value = 210;
                break;
            case "노르간논":
                value = 211;
                break;
            case "가로나":
                value = 212;
                break;
            case "윈드러너":
                value = 214;
                break;
            case "굴단":
                value = 215;
                break;
            case "알렉스트라자":
                value = 258;
                break;
            case "말퓨리온":
                value = 264;
                break;
            case "헬스크림":
                value = 293;
                break;
            case "와일드해머":
                value = 2079;
                break;
            case "렉사르":
                value = 2106;
                break;
            case "하이잘":
                value = 2107;
                break;
            case "데스윙":
                value = 2108;
                break;
            case "세나리우스":
                value = 2110;
                break;
            case "스톰레이지":
                value = 2111;
                break;
            case "줄진":
                value = 2116;
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
            case 201:
                value = "불타는 군단";
                break;
            case 205:
                value = "아즈샤라";
                break;
            case 207:
                value = "달라란";
                break;
            case 210:
                value = "듀로탄";
                break;
            case 211:
                value = "노르간논";
                break;
            case 212:
                value = "가로나";
                break;
            case 214:
                value = "윈드러너";
                break;
            case 215:
                value = "굴단";
                break;
            case 258:
                value = "알렉스트라자";
                break;
            case 264:
                value = "말퓨리온";
                break;
            case 293:
                value = "헬스크림";
                break;
            case 2079:
                value = "와일드해머";
                break;
            case 2106:
                value = "렉사르";
                break;
            case 2107:
                value = "하이잘";
                break;
            case 2108:
                value = "데스윙";
                break;
            case 2110:
                value = "세나리우스";
                break;
            case 2111:
                value = "스톰레이지";
                break;
            case 2116:
                value = "줄진";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        return value;
    }
}
