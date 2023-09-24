package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
public class Menu {

    @Id
    private String id;

    @Column
    private String menus;

    @Column
    private MealType mealType;

    @Column
    private LocalDate menuDate;

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafeteria cafeteria;

    protected Menu() {
    }

    @Builder
    public Menu(String menus, MealType mealType, LocalDate date, Campus campus, CafeteriaName cafeteria, LocalDate menuDate) {
        this.menus = menus;
        this.mealType = mealType;
        this.menuDate = menuDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Menu menu = (Menu) object;

        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
