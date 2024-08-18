package ac.knu.likeknu.job.menu.dto;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record MealMessage(@NotNull Campus campus,
                          @NotBlank String cafeteria,
                          @NotNull MealType mealType,
                          @NotEmpty List<MenuMessage> menus) {

    public static MealMessage of(Cafeteria cafeteria) {
        return MealMessage.builder()
                .campus(cafeteria.getCampus())
                .cafeteria(cafeteria.getCafeteriaName())
                .build();
    }
}
