package com.woopaca.likeknu.controller.dto.menu;

import com.woopaca.likeknu.entity.Cafeteria;

import java.time.LocalDate;
import java.util.List;

public record CafeteriaMealListResponse(String cafeteriaId, String cafeteriaName, LocalDate date,
                                        List<MealListResponse> meals) {

    public static CafeteriaMealListResponse of(Cafeteria cafeteria, LocalDate date,
                                               List<MealListResponse> meals) {
        return new CafeteriaMealListResponse(cafeteria.getId(), cafeteria.getCafeteriaName(), date, meals);
    }
}
