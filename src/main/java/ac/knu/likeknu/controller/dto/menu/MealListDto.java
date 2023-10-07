package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MealListDto {

    private String mealType;
    private String operatingTime;
    private List<MenuListDto> menus;

    @Builder
    public MealListDto(String mealType, String operatingTime, List<MenuListDto> menus) {
        this.mealType = mealType;
        this.operatingTime = operatingTime;
        this.menus = menus;
    }

}
