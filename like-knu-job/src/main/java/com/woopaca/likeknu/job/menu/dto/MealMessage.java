package com.woopaca.likeknu.job.menu.dto;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.entity.CafeteriaKey;
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

    public CafeteriaKey to() {
        return new CafeteriaKey(campus, cafeteria);
    }
}
