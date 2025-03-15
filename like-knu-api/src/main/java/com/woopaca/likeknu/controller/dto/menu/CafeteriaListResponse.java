package com.woopaca.likeknu.controller.dto.menu;

import com.woopaca.likeknu.entity.Cafeteria;

public record CafeteriaListResponse(String cafeteriaId, String cafeteriaName) {

    public static CafeteriaListResponse from(Cafeteria cafeteria) {
        return new CafeteriaListResponse(cafeteria.getId(), cafeteria.getCafeteriaName());
    }
}
