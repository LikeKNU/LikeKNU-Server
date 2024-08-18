package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.job.menu.dto.MealMessage;

public record CafeteriaKey(Campus campus, String cafeteria) {

    public static CafeteriaKey from(Cafeteria cafeteria) {
        return new CafeteriaKey(cafeteria.getCampus(), cafeteria.getCafeteriaName());
    }

    public static CafeteriaKey from(MealMessage mealMessage) {
        return new CafeteriaKey(mealMessage.campus(), mealMessage.cafeteria());
    }
}
