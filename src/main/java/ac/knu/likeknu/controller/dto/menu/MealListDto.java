package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.MealType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MealListDto {

    private String mealType;
    private String operatingTime;
    private List<MenuListDto> menus;

    @JsonIgnore
    private LocalDate date;

    @Builder
    public MealListDto(String mealType, String operatingTime, List<MenuListDto> menus, LocalDate date) {
        this.mealType = mealType;
        this.operatingTime = operatingTime;
        this.menus = menus;
        this.date = date;
    }

    public static MealListDto of(MealType mealType, Cafeteria cafeteria, Menu menu) {
        List<MenuListDto> menuList = new ArrayList<>();
        if (menu != null) {
            String[] menus = menu.getMenus().split(" ");
            for (int i = 0; i < menus.length; i++) {
                menuList.add(MenuListDto.of(i + 1, menus[i]));
            }
        }

        return MealListDto.builder()
                .mealType(mealType.getMealTypeKr())
                .operatingTime(cafeteria.getTime(mealType))
                .menus(menuList)
                .date(menu.getMenuDate())
                .build();
    }

    public static MealListDto empty(MealType mealType, LocalDate date) {
        return MealListDto.builder()
                .mealType(mealType.getMealTypeKr())
                .menus(new ArrayList<>())
                .date(date)
                .build();
    }

}
