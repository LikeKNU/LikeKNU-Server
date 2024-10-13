package ac.knu.likeknu.job.calendar.service;

import ac.knu.likeknu.domain.AcademicCalendar;
import ac.knu.likeknu.job.calendar.CalendarCache;
import ac.knu.likeknu.job.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknu.repository.AcademicCalendarRepository;
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
                .map(AcademicCalendar::of)
                .forEach(academicCalendarRepository::save);
    }
}
