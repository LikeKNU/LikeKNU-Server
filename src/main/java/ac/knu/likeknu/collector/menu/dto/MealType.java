package ac.knu.likeknu.collector.menu.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MealType {

    BREAKFAST("조식"),
    LUNCH("중식"),
    DINNER("석식");

    private final String korean;

    MealType(String korean) {
        this.korean = korean;
    }

    public static MealType of(String mealType) {
        if (mealType == null) {
            throw new IllegalArgumentException("mealType은 null일 수 없습니다");
        }

        return Arrays.stream(MealType.values())
                .filter(type -> type.korean.equals(mealType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 타입이 없습니다."));
    }
}
