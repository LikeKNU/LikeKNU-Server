package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.MealType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Cafeteria {

    @Id
    private String id;

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
        if (isWeekend(date)) {
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


    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Cafeteria cafeteria = (Cafeteria) object;

        return Objects.equals(id, cafeteria.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
