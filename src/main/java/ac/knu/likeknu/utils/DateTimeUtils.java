package ac.knu.likeknu.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

public class DateTimeUtils {

    public static LocalDate getEarliestNextAvailableDate(EnumSet<DayOfWeek> availableDayOfWeeks) {
        LocalDate date = LocalDate.now();
        while (true) {
            date = date.plusDays(1);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (availableDayOfWeeks.contains(dayOfWeek)) {
                return date;
            }
        }
    }

    public static boolean isAnotherWeek(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            return date1.getDayOfWeek().getValue() <= date2.getDayOfWeek().getValue();
        }
        return date2.getDayOfWeek().getValue() <= date1.getDayOfWeek().getValue();
    }
}
