package ac.knu.likeknu.collector.menu.dto;


import ac.knu.likeknu.collector.menu.constants.CafeteriaProxy;
import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.collector.menu.domain.CafeteriaAttributeProxy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Meal(@NotNull Campus campus, @NotBlank String cafeteria, @NotNull MealType mealType,
                   @NotEmpty List<Menu> menus) {

    public static Meal of(CafeteriaProxy cafeteria, CafeteriaAttributeProxy cafeteriaAttribute) {
        Campus campus = cafeteria.getCampus();
        String cafeteriaName = cafeteria.getName();
        MealType mealType = MealType.of(cafeteriaAttribute.getMealType());

        List<Menu> menus = cafeteriaAttribute.menuStream()
                .map(Menu::from)
                .filter(Menu::isNotEmpty)
                .toList();
        return new Meal(campus, cafeteriaName, mealType, menus);
    }

    public boolean isNotEmpty() {
        return !menus.isEmpty();
    }
}
