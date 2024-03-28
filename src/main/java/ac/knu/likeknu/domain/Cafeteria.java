package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    public String getTime(MealType mealType, LocalDate date) {
        if (mealType == null)
            return null;

        if (isWeekend(date)) {
            //주말일 때
            if (isBreakfast(mealType))
                return weekendBreakfast;
            else if (isLunch(mealType))
                return weekendLunch;
            else
                return weekendDinner;
        } else {
            //평일일 때
            if (isBreakfast(mealType))
                return weekdayBreakfast;
            else if (isLunch(mealType))
                return weekdayLunch;
            else
                return weekdayDinner;
        }
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }

    private boolean isBreakfast(MealType mealType) {
        return mealType.equals(MealType.BREAKFAST);
    }

    private boolean isLunch(MealType mealType) {
        return mealType.equals(MealType.LUNCH);
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
