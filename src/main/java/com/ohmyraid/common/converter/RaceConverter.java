package com.ohmyraid.common.converter;


import com.ohmyraid.common.enums.RaceType;

import javax.persistence.AttributeConverter;

public class RaceConverter implements AttributeConverter<String, Integer> {

    /**
     * 주어진 종족 이름으로 데이터베이스 컬럼을 변환합니다.
     *
     * @param raceName 데이터베이스에 저장되어 있는 종족의 이름
     * @return 종족 이름에 해당하는 ID
     * @throws IllegalArgumentException 만약 주어진 종족 이름이 존재하지 않을 경우 발생
     */
    @Override
    public Integer convertToDatabaseColumn(String raceName) {
        return RaceType.getIdByName(raceName).getRaceId();
    }

    /**
     * 주어진 종족 ID로 엔티티의 속성을 변환합니다.
     *
     * @param raceId 데이터베이스에 저장되어 있는 종족의 ID
     * @return raceId에 해당하는 종족의 이름
     * @throws IllegalArgumentException 만약 주어진 종족 ID가 존재하지 않을 경우 발생
     */
    @Override
    public String convertToEntityAttribute(Integer raceId) {
        return RaceType.getNameById(raceId).getRaceName();
    }
}
