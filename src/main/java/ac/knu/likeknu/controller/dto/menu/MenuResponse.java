package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.MealType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuResponse {

    private String cafeteriaId;
    private CafeteriaName cafeteriaName;
    private MealType cafeteriaMealType;
    private String cafeteriaTime;
    private List<MenuListDto> menus;

    @Builder
    public MenuResponse(String cafeteriaId, CafeteriaName cafeteriaName, MealType cafeteriaMealType, String cafeteriaTime, List<MenuListDto> menus) {
        this.cafeteriaId = cafeteriaId;
        this.cafeteriaName = cafeteriaName;
        this.cafeteriaMealType = cafeteriaMealType;
        this.cafeteriaTime = cafeteriaTime;
        this.menus = menus;
    }

    public static MenuResponse of(Cafeteria cafeteria, Menu menu) {
        List<MenuListDto> menuList = new ArrayList<>();

        if (menu.getMenus() != null){
            String[] menus = menu.getMenus().split(" ");
            for (int i = 0; i < menus.length; i++) {
                menuList.add(MenuListDto.of(i + 1, menus[i]));
            }
        }

        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .cafeteriaMealType(menu.getMealType())
                .cafeteriaTime(cafeteria.getTime(menu.getMealType()))
                .menus(menuList)
                .build();
    }

    public static MenuResponse empty(Cafeteria cafeteria, MealType mealType) {
        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .cafeteriaMealType(mealType)
                .cafeteriaTime(cafeteria.getTime(mealType))
                .build();
    }
}
