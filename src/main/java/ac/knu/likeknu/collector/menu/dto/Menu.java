package ac.knu.likeknu.collector.menu.dto;

import ac.knu.likeknu.collector.menu.domain.MenuAttribute;

import java.time.LocalDate;
import java.time.Month;
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
        LocalDate parsedDate = parseDate(date, matchFormatter);
        return new Menu(parsedDate, menu);
    }

    private static DateTimeFormatter findMatchFormatter(String date) {
        return DATE_FORMATTER.entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(date))
                .findAny()
                .orElseThrow()
                .getValue();
    }

    private static LocalDate parseDate(String date, DateTimeFormatter formatter) {
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getMonth() == Month.DECEMBER && parsedDate.getMonth() == Month.JANUARY) {
            return parsedDate.withYear(currentDate.getYear() + 1);
        }
        return parsedDate.withYear(currentDate.getYear());
    }

    public boolean isNotEmpty() {
        return !menu.isEmpty();
    }
}
