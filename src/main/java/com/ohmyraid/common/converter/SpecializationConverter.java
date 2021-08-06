package com.ohmyraid.common.converter;

import javax.persistence.AttributeConverter;

public class SpecializationConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        int value;
        switch (attribute) {
            case "비전":
                value = 62;
                break;
            case "화염":
                value = 63;
                break;
            case "냉기":
                value = 64;
                break;
            case "신성":
                value = 65;
                break;
            case "보호":
                value = 66;
                break;
            case "징벌":
                value = 70;
                break;
            case "무기":
                value = 71;
                break;
            case "분노":
                value = 72;
                break;
            case "방어":
                value = 73;
                break;
            case "조화":
                value = 102;
                break;
            case "야성":
                value = 103;
                break;
            case "수호":
                value = 104;
                break;
            case "회복":
                value = 105;
                break;
            case "혈기":
                value = 250;
                break;
            case "부정":
                value = 252;
                break;
            case "야수":
                value = 253;
                break;
            case "사격":
                value = 254;
                break;
            case "생존":
                value = 255;
                break;
            case "수양":
                value = 256;
                break;
            case "암흑":
                value = 258;
                break;
            case "암살":
                value = 259;
                break;
            case "무법":
                value = 260;
                break;
            case "잠행":
                value = 261;
                break;
            case "정기":
                value = 262;
                break;
            case "고양":
                value = 263;
                break;
            case "복원":
                value = 264;
                break;
            case "고통":
                value = 265;
                break;
            case "악마":
                value = 266;
                break;
            case "파괴":
                value = 267;
                break;
            case "양조":
                value = 268;
                break;
            case "풍운":
                value = 269;
                break;
            case "운무":
                value = 270;
                break;
            case "파멸":
                value = 577;
                break;
            case "복수":
                value = 581;
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
            case 62:
                value = "비전";
                break;
            case 63:
                value = "화염";
                break;
            case 64:
                value = "냉기";
                break;
            case 65:
                value = "신성";
                break;
            case 66:
                value = "보호";
                break;
            case 78:
                value = "징벌";
                break;
            case 71:
                value = "무기";
                break;
            case 72:
                value = "분노";
                break;
            case 73:
                value = "방어";
                break;
            case 102:
                value = "조화";
                break;
            case 103:
                value = "야성";
                break;
            case 104:
                value = "수호";
                break;
            case 105:
                value = "회복";
                break;
            case 250:
                value = "혈기";
                break;
            case 252:
                value = "부정";
                break;
            case 253:
                value = "야수";
                break;
            case 254:
                value = "사격";
                break;
            case 255:
                value = "생존";
                break;
            case 256:
                value = "수양";
                break;
            case 258:
                value = "암흑";
                break;
            case 259:
                value = "암살";
                break;
            case 260:
                value = "무법";
                break;
            case 261:
                value = "잠행";
                break;
            case 262:
                value = "정기";
                break;
            case 263:
                value = "고양";
                break;
            case 264:
                value = "복원";
                break;
            case 265:
                value = "복원";
                break;
            case 266:
                value = "악마";
                break;
            case 267:
                value = "파괴";
                break;
            case 268:
                value = "양조";
                break;
            case 269:
                value = "풍운";
                break;
            case 270:
                value = "운무";
                break;
            case 577:
                value = "파멸";
                break;
            case 581:
                value = "복수";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);

        }
        return value;
    }
}
