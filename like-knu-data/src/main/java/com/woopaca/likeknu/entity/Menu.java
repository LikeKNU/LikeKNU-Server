package com.woopaca.likeknu.entity;

import com.woopaca.likeknu.Domain;
import com.woopaca.likeknu.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Getter
@Entity
public class Menu extends BaseEntity {

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
        super(Domain.MENU);
    }

    @Builder
    public Menu(String menus, MealType mealType, LocalDate menuDate, Cafeteria cafeteria) {
        this();
        this.menus = menus;
        this.mealType = mealType;
        this.menuDate = menuDate;
        this.cafeteria = cafeteria;
    }

    public void updateMenus(String menu) {
        if (StringUtils.hasText(menu)) {
            this.menus = menu;
        }
    }
}
