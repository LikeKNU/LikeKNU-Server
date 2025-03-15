package com.woopaca.likeknu.controller.dto.schedule;

import com.woopaca.likeknu.entity.AcademicCalendar;
import com.woopaca.likeknu.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class ScheduleListDto {

    private String scheduleContents;
    private String scheduleDate;
    @JsonProperty("isToday")
    private boolean isToday;

    @Builder
    public ScheduleListDto(String scheduleContents, String scheduleDate, boolean isToday) {
        this.scheduleContents = scheduleContents;
        this.scheduleDate = scheduleDate;
        this.isToday = isToday;
    }

    public static ScheduleListDto of(AcademicCalendar academicCalendar) {
        LocalDate startDate = academicCalendar.getStartDate();
        LocalDate endDate = academicCalendar.getEndDate();
        LocalDate currentDate = LocalDate.now();

        return ScheduleListDto.builder()
                .scheduleContents(academicCalendar.getContents())
                .scheduleDate(formatScheduleDate(startDate, endDate))
                .isToday(DateTimeUtils.isDateInRange(currentDate, startDate, endDate))
                .build();
    }

    private static String formatScheduleDate(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd일(E)", Locale.KOREAN);
        DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN);

        if (startDate.equals(endDate)) {
            return startDate.format(dayFormatter);
        } else if (startDate.getMonthValue() == endDate.getMonthValue()) {
            return startDate.format(dayFormatter) + " ~ " + endDate.format(dayFormatter);
        } else {
            return startDate.format(dayFormatter) + " ~ " + endDate.format(monthDayFormatter);
        }
    }
}
