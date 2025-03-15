package com.woopaca.likeknu.job.calendar.service;

import com.woopaca.likeknu.job.calendar.CalendarCache;
import com.woopaca.likeknu.job.calendar.dto.AcademicCalendarMessage;
import com.woopaca.likeknu.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jobAcademicCalendarService")
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository academicCalendarRepository;
    private final CalendarCache calendarCache;

    public void updateCalendars(List<AcademicCalendarMessage> calendarMessages) {
        calendarMessages.stream()
                .filter(calendarCache::isAbsent)
                .map(calendarCache::cache)
                .map(AcademicCalendarMessage::toEntity)
                .forEach(academicCalendarRepository::save);
    }
}
