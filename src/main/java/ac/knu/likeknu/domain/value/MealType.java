package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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

        return Stream.of(MealType.values())
                .filter((MealType m) -> m.getHour() > hour)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
