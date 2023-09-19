package ac.knu.likeknu.domain;

import lombok.Getter;

@Getter
public enum MealType {

    BREAKFAST("조식"),
    LUNCH("중식"),
    DINNER("석식");

    private final String mealTypeKr;

    MealType(String mealTypeKr) {
        this.mealTypeKr = mealTypeKr;
    }
}
