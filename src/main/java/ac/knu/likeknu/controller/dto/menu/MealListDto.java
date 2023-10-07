package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
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

    public static MealListDto of(MealType mealType, Cafeteria cafeteria, String menu) {
        List<MenuListDto> menuList = new ArrayList<>();
        if (menu != null) {
            String[] menus = menu.split(" ");
            for (int i = 0; i < menus.length; i++) {
                menuList.add(MenuListDto.of(i + 1, menus[i]));
            }
        }

        return MealListDto.builder()
                .mealType(mealType.getMealTypeKr())
                .operatingTime(cafeteria.getTime(mealType))
                .menus(menuList)
                .build();
    }

    public static MealListDto empty(MealType mealType) {
        return MealListDto.builder()
                .mealType(mealType.getMealTypeKr())
                .operatingTime("")
                .menus(new ArrayList<>())
                .build();
    }

}
