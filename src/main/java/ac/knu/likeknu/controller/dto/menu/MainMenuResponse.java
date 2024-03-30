package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;

@Builder
public record MainMenuResponse(String cafeteriaId, String cafeteriaName, String mealType, String menus) {

    public static MainMenuResponse of(Cafeteria cafeteria, String menu) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getKorean())
                .menus(menu)
                .build();
    }

    public static MainMenuResponse empty(Cafeteria cafeteria) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getKorean())
                .build();
    }
}
