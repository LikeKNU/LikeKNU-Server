package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Cafeteria {

    @Id
    private String id;

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

    @OneToMany(mappedBy = "cafeteria")
    private List<Menu> menus = new ArrayList<>();

    protected Cafeteria() {
    }

    @Builder
    public Cafeteria(CafeteriaName cafeteriaName, String weekdayBreakfast, String weekdayLunch, String weekdayDinner, String weekendBreakfast, String weekendLunch, String weekendDinner, Campus campus) {
        this.cafeteriaName = cafeteriaName;
        this.weekdayBreakfast = weekdayBreakfast;
        this.weekdayLunch = weekdayLunch;
        this.weekdayDinner = weekdayDinner;
        this.weekendBreakfast = weekendBreakfast;
        this.weekendLunch = weekendLunch;
        this.weekendDinner = weekendDinner;
        this.campus = campus;
    }

    public String getTime(MealType mealType) {
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
