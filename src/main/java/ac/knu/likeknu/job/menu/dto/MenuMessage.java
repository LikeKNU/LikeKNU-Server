package ac.knu.likeknu.job.menu.dto;

import ac.knu.likeknu.domain.Menu;

import java.time.LocalDate;

public record MenuMessage(LocalDate date, String menu) {

    public static MenuMessage from(Menu menu) {
        return new MenuMessage(menu.getMenuDate(), menu.getMenus());
    }

    public static MenuMessage from(ac.knu.likeknu.collector.menu.dto.Menu menu) {
        return new MenuMessage(menu.date(), menu.menu());
    }
}
