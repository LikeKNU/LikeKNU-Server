package ac.knu.likeknu.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Menu {

    @Id
    private String menuId;

    @Column
    private String menus;

    @Column
    private MealType mealType;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Builder
    public Menu(String menus, MealType mealType, LocalDate date, Campus campus) {
        this.menus = menus;
        this.mealType = mealType;
        this.date = date;
        this.campus = campus;
    }
}
