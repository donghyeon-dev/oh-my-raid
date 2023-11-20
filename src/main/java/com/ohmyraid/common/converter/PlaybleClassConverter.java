package com.ohmyraid.common.converter;

import com.ohmyraid.common.enums.PlayableClassType;

import javax.persistence.AttributeConverter;

public class PlaybleClassConverter implements AttributeConverter<String, Integer> {

    /**
     * 전달된 playableClassName에 해당하는 PlayableClassType을 검색하고 이와 맵핑되는 playableClassId를 데이터베이스 컬럼으로 반환합니다.
     * 해당하는 PlayableClassType이 없을 경우 IllegalArgumentException이 발생합니다.
     *
     * @param playableClassName playable class type의 name
     * @return 데이터베이스에 맵핑되는 playableClassId
     * @throws IllegalArgumentException playableClassName이 PlayableClassType에 존재하지 않을 경우 발생
     */
    @Override
    public Integer convertToDatabaseColumn(String playableClassName) {
        return PlayableClassType.getIdByName(playableClassName).getPlayableClassId();
    }

    ;

    /**
     * 전달된 playableClassId에 해당하는 플레이어블 클래스 이름을 반환합니다.
     * 해당하는 PlayableClassType이 없을 경우 IllegalArgumentException이 발생합니다.
     *
     * @param playableClassId 플레이어블 클래스 타입의 ID
     * @return 플레이어블 클래스 타입의 이름
     * @throws IllegalArgumentException playableClassId가 PlayableClassType에 존재하지 않을 경우 발생
     */
    @Override
    public String convertToEntityAttribute(Integer playableClassId) {
        return PlayableClassType.getNameById(playableClassId).getPlayableClassName();
    }
}
