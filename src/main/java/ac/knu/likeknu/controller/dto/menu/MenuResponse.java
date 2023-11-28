package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
public class MenuResponse {

    private String cafeteriaId;
    private String cafeteriaName;
    private List<MealListDto> today;
    private List<MealListDto> tomorrow;

    @Builder
    public MenuResponse(String cafeteriaId, String cafeteriaName, List<MealListDto> today, List<MealListDto> tomorrow) {
        this.cafeteriaId = cafeteriaId;
        this.cafeteriaName = cafeteriaName;
        this.today = today;
        this.tomorrow = tomorrow;
    }

    public static MenuResponse of(Cafeteria cafeteria, Map<LocalDate, List<MealListDto>> mealList) {
        LocalDate now = LocalDate.now();
        return MenuResponse.builder()
                .cafeteriaId(cafeteria.getId())
                .cafeteriaName(cafeteria.getCafeteriaName().getCafeteriaName())
                .today(mealList.get(now))
                .tomorrow(mealList.get(now.plusDays(1)))
                .build();
    }

}
