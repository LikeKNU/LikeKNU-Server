package ac.knu.likeknu.controller.dto.response;

import ac.knu.likeknu.domain.Cafeteria;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainMenuResponse {

    private final String cafeteriaId;
    private final String cafeteriaName;
    private final List<MenuListDto> menus;

    @Builder
    public MainMenuResponse(String cafeteriaId, String cafeteriaName, List<MenuListDto> menus) {
        this.cafeteriaId = cafeteriaId;
        this.cafeteriaName = cafeteriaName;
        this.menus = menus;
    }

    public static MainMenuResponse of(Cafeteria cafeteria, String menu) {
        String[] menus = menu.split(" ");
        List<MenuListDto> menuList = new ArrayList<>();
        for (int i = 1; i <= menus.length; i++) {
            menuList.add(MenuListDto.of(i, menus[i]));
        }

        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName().getCafeteriaName())
                .menus(menuList)
                .build();
    }

    public static MainMenuResponse empty(Cafeteria cafeteria) {
        return MainMenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName().getCafeteriaName())
                .build();
    }
}

@Getter
class MenuListDto {

    private final int menuId;
    private final String menuName;

    public MenuListDto(int menuId, String menuName) {
        this.menuId = menuId;
        this.menuName = menuName;
    }

    public static MenuListDto of(int index, String menuName) {
        return new MenuListDto(index, menuName);
    }
}
