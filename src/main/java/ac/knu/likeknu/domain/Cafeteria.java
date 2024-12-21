package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.MealType;
import ac.knu.likeknu.utils.DateTimeUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Cafeteria extends BaseEntity {

    private String cafeteriaName;

    private String weekdayBreakfast;

    private String weekdayLunch;

    private String weekdayDinner;

    private String weekendBreakfast;

    private String weekendLunch;

    private String weekendDinner;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    private Integer sequence;

    @OneToMany(mappedBy = "cafeteria")
    private List<Menu> menus = new ArrayList<>();

    protected Cafeteria() {
    }

    @Builder
    public Cafeteria(String cafeteriaName, String weekdayBreakfast, String weekdayLunch, String weekdayDinner, String weekendBreakfast, String weekendLunch, String weekendDinner, Campus campus, Integer sequence) {
        this.cafeteriaName = cafeteriaName;
        this.weekdayBreakfast = weekdayBreakfast;
        this.weekdayLunch = weekdayLunch;
        this.weekdayDinner = weekdayDinner;
        this.weekendBreakfast = weekendBreakfast;
        this.weekendLunch = weekendLunch;
        this.weekendDinner = weekendDinner;
        this.campus = campus;
        this.sequence = sequence;
    }

    public boolean isOperate(MealType mealType, LocalDate date) {
        String operatingTime = getOperatingTime(mealType, date);
        return operatingTime != null;
    }

    public String getOperatingTime(MealType mealType, LocalDate date) {
        if (DateTimeUtils.isWeekend(date)) {
            if (mealType.equals(MealType.BREAKFAST)) {
                return weekendBreakfast;
            }
            if (mealType.equals(MealType.LUNCH)) {
                return weekendLunch;
            }
            if (mealType.equals(MealType.DINNER))
                return weekendDinner;
        } else {
            if (mealType.equals(MealType.BREAKFAST)) {
                return weekdayBreakfast;
            }
            if (mealType.equals(MealType.LUNCH)) {
                return weekdayLunch;
            }
            if (mealType.equals(MealType.DINNER)) {
                return weekdayDinner;
            }
        }
        return null;
    }
}
