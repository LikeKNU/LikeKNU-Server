package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Domain;
import ac.knu.likeknu.domain.value.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Cafeteria extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CafeteriaName cafeteriaName;

    @Column
    private String weekdayBreakfast;

    @Column
    private String weekdayLunch;

    @Column
    private String weekdayDinner;

    @Column
    private String weekendBreakfast;

    @Column
    private String weekendLunch;

    @Column
    private String weekendDinner;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    protected Cafeteria() {
        super(Domain.CAFETERIA);
    }

    @Builder
    public Cafeteria(CafeteriaName cafeteriaName, String weekdayBreakfast, String weekdayLunch, String weekdayDinner, String weekendBreakfast, String weekendLunch, String weekendDinner, Campus campus) {
        this();
        this.cafeteriaName = cafeteriaName;
        this.weekdayBreakfast = weekdayBreakfast;
        this.weekdayLunch = weekdayLunch;
        this.weekdayDinner = weekdayDinner;
        this.weekendBreakfast = weekendBreakfast;
        this.weekendLunch = weekendLunch;
        this.weekendDinner = weekendDinner;
        this.campus = campus;
    }

    public String getTime() {
        MealType mealType = MealType.of();

        if(mealType == null)
            return null;

        if(isWeekend()) {
            //주말일 때
            if(isBreakfast(mealType))
                return weekendBreakfast;
            else if(isLunch(mealType))
                return weekendLunch;
            else
                return weekendDinner;
        }
        else {
            //평일일 때
            if(isBreakfast(mealType))
                return weekdayBreakfast;
            else if(isLunch(mealType))
                return weekdayLunch;
            else
                return weekdayDinner;
        }
    }

    private boolean isWeekend() {
        return LocalDateTime.now().getDayOfWeek().getValue() >= 6;
    }

    private boolean isBreakfast(MealType mealType) {
        return mealType.equals(MealType.BREAKFAST);
    }

    private boolean isLunch(MealType mealType) {
        return mealType.equals(MealType.LUNCH);
    }
}
