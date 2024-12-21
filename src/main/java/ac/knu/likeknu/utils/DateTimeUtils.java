package ac.knu.likeknu.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.EnumSet;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

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

    public static LocalDate getPreviousOrSameDate(DayOfWeek dayOfWeek) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.with(TemporalAdjusters.previousOrSame(dayOfWeek));
    }

    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }
}
