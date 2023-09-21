package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

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
}
