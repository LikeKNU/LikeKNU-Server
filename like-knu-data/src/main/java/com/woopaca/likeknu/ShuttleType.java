package com.woopaca.likeknu;

import com.woopaca.likeknu.exception.BusinessException;

import java.util.Arrays;

public enum ShuttleType {

    SCHOOL_BUS, CIRCULAR_BUS;

    public static ShuttleType of(String shuttle) {
        return Arrays.stream(ShuttleType.values())
                .filter(shuttleType -> shuttleType.name().toLowerCase().startsWith(shuttle.split("-")[0]))
                .findAny()
                .orElseThrow(() -> new BusinessException("Invalid shuttle type!"));
    }
}
