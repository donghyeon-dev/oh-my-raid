package com.ohmyraid.common.converter;


import javax.persistence.AttributeConverter;

public class RaceConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        int value;
        switch (attribute) {
            case "인간":
                value = 1;
                break;
            case "오크":
                value = 2;
                break;
            case "드워프":
                value = 3;
                break;
            case "나이트 엘프":
                value = 4;
                break;
            case "언데드":
                value = 5;
                break;
            case "타우렌":
                value = 6;
                break;
            case "노움":
                value = 7;
                break;
            case "트롤":
                value = 8;
                break;
            case "고블린":
                value = 9;
                break;
            case "블러드 엘프":
                value = 10;
                break;
            case "드레나이":
                value = 11;
                break;
            case "늑대인간":
                value = 22;
                break;
            case "판다렌":
                value = 24;
                break;
            case "나이트본":
                value = 27;
                break;
            case "높은산 타우렌":
                value = 28;
                break;
            case "공허엘프":
                value = 29;
                break;
            case "빛벼림 드레나이":
                value = 30;
                break;
            case "잔달라 트롤":
                value = 31;
                break;
            case "쿨티란":
                value = 32;
                break;
            case "검은무쇠 드워프":
                value = 34;
                break;
            case "불페라":
                value = 35;
                break;
            case "마그하르 오크":
                value = 36;
                break;
            case "기계노움":
                value = 37;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attribute);
        }
        ;
        return value;
    }

    @Override
    public String convertToEntityAttribute(Integer dbData) {
        String value = "";
        switch (dbData) {
            case 1:
                value = "인간";
                break;
            case 2:
                value = "오크";
                break;
            case 3:
                value = "드워프";
                break;
            case 4:
                value = "나이트 엘프";
                break;
            case 5:
                value = "언데드";
                break;
            case 6:
                value = "타우렌";
                break;
            case 7:
                value = "노움";
                break;
            case 8:
                value = "트롤";
                break;
            case 9:
                value = "고블린";
                break;
            case 10:
                value = "블러드 엘프";
                break;
            case 11:
                value = "드레나이";
                break;
            case 22:
                value = "늑대인간";
                break;
            case 24:
                value = "판다렌";
                break;
            case 27:
                value = "나이트본";
                break;
            case 28:
                value = "높은산 타우렌";
                break;
            case 29:
                value = "공허엘프";
                break;
            case 30:
                value = "빛벼림 드레나이";
                break;
            case 31:
                value = "잔달라 트롤";
                break;
            case 32:
                value = "쿨티란";
                break;
            case 34:
                value = "검은무쇠 드워프";
                break;
            case 35:
                value = "불페라";
                break;
            case 36:
                value = "마그하르 오크";
                break;
            case 37:
                value = "기계노움";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        ;
        return value;
    }
}
