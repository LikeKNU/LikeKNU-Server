package com.woopaca.likeknu.job.menu.dto;

import com.woopaca.likeknu.collector.menu.dto.MenuDto;
import com.woopaca.likeknu.entity.Menu;

import java.time.LocalDate;

public record MenuMessage(LocalDate date, String menu, String mealType) {

    public static MenuMessage from(Menu menu) {
        return new MenuMessage(menu.getMenuDate(), menu.getMenus(), menu.getMealType().name());
    }

    public static MenuMessage of(MenuDto menu, String mealType) {
        return new MenuMessage(menu.date(), menu.menu(), mealType);
    }
}
