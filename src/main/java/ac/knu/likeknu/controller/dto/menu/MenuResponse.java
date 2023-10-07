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
    private List<MealListDto> meal;

    @Builder
    public MenuResponse(String cafeteriaId, CafeteriaName cafeteriaName) {
        this.cafeteriaId = cafeteriaId;
        this.cafeteriaName = cafeteriaName;
    }

    public static MenuResponse of(Cafeteria cafeteria) {

        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .build();
    }

    public static MenuResponse empty(Cafeteria cafeteria) {
        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName())
                .build();
    }
}
