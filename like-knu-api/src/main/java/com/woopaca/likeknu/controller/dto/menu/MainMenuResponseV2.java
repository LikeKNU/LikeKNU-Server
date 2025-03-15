package com.woopaca.likeknu.controller.dto.menu;

import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.entity.Cafeteria;
import lombok.Builder;

@Builder
public record MainMenuResponseV2(String cafeteriaId, String cafeteriaName, String mealType, String menus) {

    public static MainMenuResponseV2 of(Cafeteria cafeteria, String menu) {
        return MainMenuResponseV2.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getKorean())
                .menus(menu)
                .build();
    }

    public static MainMenuResponseV2 empty(Cafeteria cafeteria) {
        return MainMenuResponseV2.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getKorean())
                .build();
    }
}
