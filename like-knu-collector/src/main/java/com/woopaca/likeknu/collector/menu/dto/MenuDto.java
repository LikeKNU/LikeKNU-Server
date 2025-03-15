package com.woopaca.likeknu.collector.menu.dto;

import com.woopaca.likeknu.collector.menu.domain.MenuAttribute;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public record MenuDto(LocalDate date, String menu) {

    private static final Map<Predicate<String>, DateTimeFormatter> DATE_FORMATTER = new ConcurrentHashMap<>();

    static {
        DATE_FORMATTER.put(date -> date.contains("."), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        DATE_FORMATTER.put(date -> date.contains("월") && date.contains("일"), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

    public static MenuDto from(MenuAttribute menuAttribute) {
        String date = menuAttribute.getDate();
        String menu = menuAttribute.getMenu();
        if (!menu.contains("1,000원") && !menu.contains("50명한정")) {
            menu = menu.replaceAll(", ", " ")
                    .replaceAll(",", " ")
                    .replaceAll(" plus ", " ")
                    .replaceAll("/", " ");
        }

        DateTimeFormatter matchFormatter = findMatchFormatter(date);
        LocalDate parsedDate = parseDate(date, matchFormatter);
        return new MenuDto(parsedDate, menu);
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
