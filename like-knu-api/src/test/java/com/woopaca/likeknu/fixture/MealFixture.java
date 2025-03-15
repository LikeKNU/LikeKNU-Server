package com.woopaca.likeknu.fixture;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.entity.Menu;

import java.time.LocalDate;

public final class MealFixture {

    public static Cafeteria createCafeteria() {
        return Cafeteria.builder()
                .cafeteriaName("학생식당")
                .campus(Campus.SINGWAN)
                .build();
    }

    public static Menu createMenuWith(Cafeteria cafeteria) {
        return Menu.builder()
                .menus("밥 국 김치")
                .mealType(MealType.LUNCH)
                .menuDate(LocalDate.now())
                .cafeteria(cafeteria)
                .build();
    }
}
