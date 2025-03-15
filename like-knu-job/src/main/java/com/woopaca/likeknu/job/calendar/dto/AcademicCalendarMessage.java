package com.woopaca.likeknu.job.calendar.dto;

import com.woopaca.likeknu.entity.AcademicCalendar;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class AcademicCalendarMessage {

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private String contents;

    @Builder
    public AcademicCalendarMessage(LocalDate startDate, LocalDate endDate, String contents) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
    }

    public AcademicCalendar toEntity() {
        return AcademicCalendar.builder()
                .startDate(startDate)
                .endDate(endDate)
                .contents(contents)
                .build();
    }

    public static AcademicCalendarMessage from(AcademicCalendar academicCalendar) {
        return AcademicCalendarMessage.builder()
                .startDate(academicCalendar.getStartDate())
                .endDate(academicCalendar.getEndDate())
                .contents(academicCalendar.getContents())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AcademicCalendarMessage that = (AcademicCalendarMessage) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, contents);
    }
}
