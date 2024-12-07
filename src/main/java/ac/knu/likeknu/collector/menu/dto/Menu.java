package ac.knu.likeknu.collector.menu.dto;

import ac.knu.likeknu.collector.menu.domain.MenuAttribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public record Menu(LocalDate date, String menu) {

    private static final Map<Predicate<String>, DateTimeFormatter> DATE_FORMATTER = new ConcurrentHashMap<>();

    static {
        DATE_FORMATTER.put(date -> date.contains("."), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        DATE_FORMATTER.put(date -> date.contains("월") && date.contains("일"), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

    public static Menu from(MenuAttribute menuAttribute) {
        String date = menuAttribute.getDate();
        String menu = menuAttribute.getMenu();
        menu = menu.replaceAll(", ", " ")
                .replaceAll(",", " ")
                .replaceAll(" plus ", " ")
                .replaceAll("/", " ");
        DateTimeFormatter matchFormatter = findMatchFormatter(date);
        return new Menu(LocalDate.parse(date, matchFormatter), menu);
    }

    private static DateTimeFormatter findMatchFormatter(String date) {
        return DATE_FORMATTER.entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(date))
                .findAny()
                .orElseThrow()
                .getValue();
    }

    public boolean isNotEmpty() {
        return !menu.isEmpty();
    }
}
