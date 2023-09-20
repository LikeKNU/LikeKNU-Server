package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Domain;
import ac.knu.likeknu.domain.value.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Menu extends BaseEntity {

    @Column
    private String menus;

    @Column
    private MealType mealType;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    protected Menu() {
        super(Domain.MENU);
    }

    @Builder
    public Menu(String menus, MealType mealType, LocalDate date, Campus campus) {
        this();
        this.menus = menus;
        this.mealType = mealType;
        this.date = date;
        this.campus = campus;
    }
}
