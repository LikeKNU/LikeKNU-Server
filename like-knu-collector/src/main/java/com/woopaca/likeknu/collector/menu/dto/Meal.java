package com.woopaca.likeknu.collector.menu.dto;


import com.woopaca.likeknu.collector.menu.constants.CafeteriaProxy;
import com.woopaca.likeknu.collector.menu.constants.Campus;
import com.woopaca.likeknu.collector.menu.domain.CafeteriaAttributeProxy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Meal(@NotNull Campus campus, @NotBlank String cafeteria, @NotNull MealType mealType,
                   @NotEmpty List<MenuDto> menus) {

    public static Meal of(CafeteriaProxy cafeteria, CafeteriaAttributeProxy cafeteriaAttribute) {
        Campus campus = cafeteria.getCampus();
        String cafeteriaName = cafeteria.getName();
        MealType mealType = MealType.of(cafeteriaAttribute.getMealType());

        List<MenuDto> menus = cafeteriaAttribute.menuStream()
                .map(MenuDto::from)
                .filter(MenuDto::isNotEmpty)
                .toList();
        return new Meal(campus, cafeteriaName, mealType, menus);
    }

    public boolean isNotEmpty() {
        return !menus.isEmpty();
    }
}
