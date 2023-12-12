package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RetryMethodType {

    GET_ACCOUNT_CHRACTERS("getChractersByAccount"),
    GET_CHARACTER("getCharacterSpec"),
    GET_CHARACTER_RAID_ENCOUNTER("getRaidEncounter");

    private final String methodName;


    public static RetryMethodType getTypeByMethodName(String methodName) {
        return Arrays.stream(RetryMethodType.values())
                .filter(targetType -> targetType.getMethodName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described methodName."));
    }
}
