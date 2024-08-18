package ac.knu.likeknu.collector.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record AcademicCalendar(@NotBlank LocalDate startDate, @NotBlank LocalDate endDate, @NotBlank String contents) {

    public static AcademicCalendar of(String yearValue, String date, String contents) {
        LocalDate startDate;
        LocalDate endDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        if (date.contains("~")) {
            String[] dates = date.split(" ~ ");
            String start = dates[0];
            String end = dates[1];

            startDate = LocalDate.parse(String.join(".", yearValue, start), formatter);
            endDate = LocalDate.parse(String.join(".", yearValue, end), formatter);

            if (startDate.isAfter(endDate)) {
                if (isNextYear(yearValue)) {
                    startDate = startDate.minusYears(1);
                } else {
                    endDate = endDate.plusYears(1);
                }
            }
        } else {
            startDate = endDate = LocalDate.parse(String.join(".", yearValue, date), formatter);
        }

        return AcademicCalendar.builder()
                .startDate(startDate)
                .endDate(endDate)
                .contents(contents)
                .build();
    }

    private static boolean isNextYear(String yearValue) {
        return LocalDate.now().getYear() + 1 == Integer.parseInt(yearValue);
    }
}
