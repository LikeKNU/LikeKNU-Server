package ac.knu.likeknu.domain;

import lombok.Getter;

@Getter
public enum Week {

    WEEKDAY("평일"),
    WEEKEND("주말/공휴일");

    private String weeks;

    Week(String weeks) {
        this.weeks = weeks;
    }
}
