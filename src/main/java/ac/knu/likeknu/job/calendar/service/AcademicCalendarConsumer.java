package ac.knu.likeknu.job.calendar.service;

import ac.knu.likeknu.collector.calendar.dto.AcademicCalendar;
import ac.knu.likeknu.job.calendar.dto.AcademicCalendarMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicCalendarConsumer {

    private final AcademicCalendarService academicCalendarService;

    @EventListener
    public void consumeAcademicCalendarMessages(@Valid List<AcademicCalendar> academicCalendars) {
        List<AcademicCalendarMessage> calendarMessages = academicCalendars.stream()
                .map(academicCalendar -> AcademicCalendarMessage.builder()
                        .contents(academicCalendar.contents())
                        .startDate(academicCalendar.startDate())
                        .endDate(academicCalendar.endDate())
                        .build())
                .toList();
        academicCalendarService.updateCalendars(calendarMessages);
    }
}
