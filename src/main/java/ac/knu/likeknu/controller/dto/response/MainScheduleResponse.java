package ac.knu.likeknu.controller.dto.response;

import ac.knu.likeknu.domain.AcademicCalendar;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
public class MainScheduleResponse {

    private String scheduleId;
    private String scheduleContents;
    private String scheduleDate;
    private boolean today;

    @Builder
    public MainScheduleResponse(String scheduleId, String scheduleContents, String scheduleDate, boolean today) {
        this.scheduleId = scheduleId;
        this.scheduleContents = scheduleContents;
        this.scheduleDate = scheduleDate;
        this.today = today;
    }

    public static MainScheduleResponse of(AcademicCalendar calendar) {
        LocalDate start = calendar.getStartDate();
        LocalDate end = calendar.getEndDate();

        return MainScheduleResponse.builder()
                .scheduleId(calendar.getId())
                .scheduleContents(calendar.getContents())
                .scheduleDate(dateParser(start, end))
                .today(isBetween(start, end))
                .build();
    }

    private static boolean isBetween(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        return startDate.compareTo(now) >= 0 && endDate.compareTo(now) <=0;
    }

    private static String dateParser(LocalDate startDate, LocalDate endDate) {
        String date = dateFormatter(startDate);

        if(!startDate.isEqual(endDate)) {
            date += " ~ " + dateFormatter(endDate);
        }

        return date;
    }

    private static String dateFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

        return MonthDay.from(date).format(formatter) + "("
                + date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";
    }
}
