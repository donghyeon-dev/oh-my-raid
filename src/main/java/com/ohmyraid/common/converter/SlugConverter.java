package com.ohmyraid.common.converter;

import com.ohmyraid.common.enums.SlugType;

import javax.persistence.AttributeConverter;

public class SlugConverter implements AttributeConverter<String, Integer> {

    /**
     * 서버 이름을 기반으로 해당하는 서버 ID를 데이터베이스 컬럼으로 변환합니다.
     * 만약 일치하는 서버가 없다면, IllegalArgumentException 예외를 던집니다.
     *
     * @param slugName 데이터베이스로 변환하려는 서버 이름입니다.
     * @return 서버 이름에 해당하는 서버 ID를 반환합니다.
     * @throws IllegalArgumentException 일치하는 서버가 없을 경우를 나타냅니다.
     */
    @Override
    public Integer convertToDatabaseColumn(String slugName) {
        return SlugType.getTypeByName(slugName).getSlugId();
    }

    /**
     * 서버Id를 기반으로 서버 이름을 반환하는 함수입니다.
     * 서버Id는 enum에서 함수 getSlugName()로 얻어진다.
     * Enum SlugType에 해당 slugId가 없는 경우 IllegalArgumentException을 던집니다.
     *
     * @param slugId 서버 ID
     * @return 서버 이름을 반환합니다. slugId에 해당하는 SlugType이 없으면 IllegalArgumentException을 던집니다.
     * @throws IllegalArgumentException slugrId에 해당하는 SlugType이 없을 때 발생합니다.
     */
    @Override
    public String convertToEntityAttribute(Integer slugId) {
        return SlugType.getTypeById(slugId).getSlugName();
    }
}
