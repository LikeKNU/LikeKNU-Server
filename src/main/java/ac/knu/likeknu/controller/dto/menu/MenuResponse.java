package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.CafeteriaName;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuResponse implements Comparable<MenuResponse> {

    private String cafeteriaId;
    private String cafeteriaName;
    private List<MealListDto> meal;

    @Builder
    public MenuResponse(String cafeteriaId, String cafeteriaName, List<MealListDto> meal) {
        this.cafeteriaId = cafeteriaId;
        this.cafeteriaName = cafeteriaName;
        this.meal = meal;
    }

    public static MenuResponse of(Cafeteria cafeteria, List<MealListDto> mealList) {
        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName().getCafeteriaName())
                .meal(mealList)
                .build();
    }

    @Override
    public int compareTo(MenuResponse o) {
        return CafeteriaName.of(this.cafeteriaName).compareTo(CafeteriaName.of(o.cafeteriaName));
    }
}
