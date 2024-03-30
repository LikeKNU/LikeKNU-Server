package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Getter
public enum MealType {

    BREAKFAST("아침", 9),
    LUNCH("점심", 14),
    DINNER("저녁", 19);

    private final String korean;
    private final int hour;

    MealType(String korean, int hour) {
        this.korean = korean;
        this.hour = hour;
    }

    public static MealType now() {
        int hour = LocalDateTime.now()
                .getHour();

        return Stream.of(MealType.values())
                .filter(mealType -> mealType.getHour() > hour)
                .findFirst()
                .orElse(DINNER);
    }
}
