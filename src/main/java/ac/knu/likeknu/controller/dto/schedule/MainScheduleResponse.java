package ac.knu.likeknu.controller.dto.schedule;

import ac.knu.likeknu.domain.AcademicCalendar;
import ac.knu.likeknu.utils.DateTimeUtils;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Builder
public record MainScheduleResponse(
        String scheduleId,
        String scheduleContents,
        String scheduleDate,
        boolean isToday
) {

    public static MainScheduleResponse of(AcademicCalendar calendar) {
        LocalDate startDate = calendar.getStartDate();
        LocalDate endDate = calendar.getEndDate();
        LocalDate currentDate = LocalDate.now();

        return MainScheduleResponse.builder()
                .scheduleId(calendar.getId())
                .scheduleContents(calendar.getContents())
                .scheduleDate(formatDateRange(startDate, endDate))
                .isToday(DateTimeUtils.isDateInRange(currentDate, startDate, endDate))
                .build();
    }

    private static String formatDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd (E)", Locale.KOREAN);
        String startFormatted = startDate.format(formatter);

        if (startDate.isEqual(endDate)) {
            return startFormatted;
        } else {
            String endFormatted = endDate.format(formatter);
            return startFormatted + " ~ " + endFormatted;
        }
    }
}
