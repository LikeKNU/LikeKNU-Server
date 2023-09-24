package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum MealType {

    BREAKFAST("조식", 9),
    LUNCH("중식", 14),
    DINNER("석식", 19);

    private final String mealTypeKr;
    private final int hour;

    MealType(String mealTypeKr, int hour) {
        this.mealTypeKr = mealTypeKr;
        this.hour = hour;
    }

    public static MealType now() {
        int hour = LocalDateTime.now().getHour();

        for(MealType m : MealType.values()) {
            if(m.getHour() > hour)
                return m;
        }

        return null;
    }
}
