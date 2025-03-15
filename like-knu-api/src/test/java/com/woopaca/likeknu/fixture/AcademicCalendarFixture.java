package com.woopaca.likeknu.fixture;

import com.woopaca.likeknu.entity.AcademicCalendar;

import java.time.LocalDate;

public final class AcademicCalendarFixture {

    public static AcademicCalendar createAcademicCalendar() {
        LocalDate currentDate = LocalDate.now();
        return AcademicCalendar.builder()
                .contents("like-knu")
                .startDate(currentDate)
                .endDate(currentDate.plusDays(3))
                .build();
    }
}
