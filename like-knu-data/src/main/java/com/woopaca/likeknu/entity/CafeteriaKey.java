package com.woopaca.likeknu.entity;

import com.woopaca.likeknu.Campus;

public record CafeteriaKey(Campus campus, String cafeteria) {

    public static CafeteriaKey from(Cafeteria cafeteria) {
        return new CafeteriaKey(cafeteria.getCampus(), cafeteria.getCafeteriaName());
    }
}
