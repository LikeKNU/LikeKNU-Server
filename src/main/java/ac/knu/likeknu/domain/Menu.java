package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
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
    public Menu(String menus, MealType mealType, LocalDate menuDate, Cafeteria cafeteria) {
        this.menus = menus;
        this.mealType = mealType;
        this.menuDate = menuDate;
        this.cafeteria = cafeteria;
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
