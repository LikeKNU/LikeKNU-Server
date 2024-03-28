package ac.knu.likeknu.controller.dto.main;

import ac.knu.likeknu.controller.dto.menu.MenuListDto;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record MainMenuResponse(String cafeteriaId, String cafeteriaName, String mealType, List<MenuListDto> menus) {

    public static MainMenuResponse of(Cafeteria cafeteria, String menu) {
        List<MenuListDto> menuList = new ArrayList<>();
        if (menu != null) {
            String[] menus = menu.split(" ");
            for (int i = 0; i < menus.length; i++) {
                menuList.add(MenuListDto.of(i + 1, menus[i]));
            }
        }

        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getMealTypeKr())
                .menus(menuList)
                .build();
    }

    public static MainMenuResponse empty(Cafeteria cafeteria) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .mealType(MealType.now().getMealTypeKr())
                .menus(new ArrayList<>())
                .build();
    }
}
