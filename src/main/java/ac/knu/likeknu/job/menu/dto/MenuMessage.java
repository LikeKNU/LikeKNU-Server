package ac.knu.likeknu.job.menu.dto;

import ac.knu.likeknu.domain.Menu;

import java.time.LocalDate;

public record MenuMessage(LocalDate date, String menu, String mealType) {

    public static MenuMessage from(Menu menu) {
        return new MenuMessage(menu.getMenuDate(), menu.getMenus(), menu.getMealType().name());
    }

    public static MenuMessage of(ac.knu.likeknu.collector.menu.dto.Menu menu, String mealType) {
        return new MenuMessage(menu.date(), menu.menu(), mealType);
    }
}
