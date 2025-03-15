package com.woopaca.likeknu.controller.dto.menu;

import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.entity.Cafeteria;
import lombok.Builder;

@Builder
public record MainMenuResponse(String cafeteriaId, String cafeteriaName, String mealType, String menus) {

    public static MainMenuResponse of(Cafeteria cafeteria, String menu) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(changeCafeteriaName(cafeteria.getCafeteriaName()))
                .mealType(MealType.now().getKorean())
                .menus(menu)
                .build();
    }

    private static String changeCafeteriaName(String cafeteriaName) {
        if (cafeteriaName.equals("홍/은/해")) {
            return "은행사/비전";
        }
        if (cafeteriaName.equals("드/비/블")) {
            return "드림";
        }
        return cafeteriaName;
    }

    public static MainMenuResponse empty(Cafeteria cafeteria) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(changeCafeteriaName(cafeteria.getCafeteriaName()))
                .mealType(MealType.now().getKorean())
                .build();
    }
}
