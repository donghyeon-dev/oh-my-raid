package com.ohmyraid.common.converter;

import com.ohmyraid.common.enums.SpecializationType;

import javax.persistence.AttributeConverter;

public class SpecializationConverter implements AttributeConverter<String, Integer> {

    /**
     * 해당 전문화 이름을 데이터베이스 컬럼 값(전문화 ID)로 변환합니다.
     *
     * @param specializationName 변환하려는 전문화 이름
     * @return 전문화 이름에 해당하는 전문화 ID
     * @throws IllegalArgumentException 전문화 이름이 설명되지 않은 경우 예외 발생
     */
    @Override
    public Integer convertToDatabaseColumn(String specializationName) {
        return SpecializationType.getIdByName(specializationName).getSpecializationId();
    }

    /**
     * 주어진 전문화 ID에 해당하는 전문화 이름을 반환합니다.
     *
     * @param specializationId 변환하려는 전문화 ID
     * @return 전문화 ID에 해당하는 전문화 이름
     * @throws IllegalArgumentException 전문화 ID가 설명되지 않은 경우 예외 발생
     */
    @Override
    public String convertToEntityAttribute(Integer specializationId) {
        return SpecializationType.getNameById(specializationId).getSpecializationName();
    }
}
