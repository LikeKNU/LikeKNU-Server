package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;

@Builder
public record MealListResponse(String menuId, String mealType, String operatingTime, String menus) {

    public static MealListResponse of(Menu menu, Cafeteria cafeteria) {
        MealType mealType = menu.getMealType();
        String operatingTime = cafeteria.getOperatingTime(mealType, menu.getMenuDate());
        return new MealListResponse(menu.getId(), mealType.getKorean(), operatingTime, menu.getMenus());
    }

    public static MealListResponse empty(MealType mealType, String operatingTime) {
        return MealListResponse.builder()
                .mealType(mealType.getKorean())
                .operatingTime(operatingTime)
                .build();
    }
}
