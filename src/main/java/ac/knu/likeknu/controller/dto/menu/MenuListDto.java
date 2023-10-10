package ac.knu.likeknu.controller.dto.menu;

import lombok.Getter;

@Getter
public class MenuListDto {

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
