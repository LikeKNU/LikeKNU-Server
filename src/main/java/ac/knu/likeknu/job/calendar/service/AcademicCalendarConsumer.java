package ac.knu.likeknu.job.calendar.service;

import ac.knu.likeknu.collector.calendar.dto.AcademicCalendar;
import ac.knu.likeknu.job.calendar.dto.AcademicCalendarMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademicCalendarConsumer {

    private final AcademicCalendarService academicCalendarService;

    @EventListener
    public void consumeAcademicCalendarMessage(@Valid AcademicCalendar academicCalendar) {
        AcademicCalendarMessage academicCalendarMessage = AcademicCalendarMessage.builder()
                .contents(academicCalendar.contents())
                .startDate(academicCalendar.startDate())
                .endDate(academicCalendar.endDate())
                .build();
        academicCalendarService.updateCalendar(academicCalendarMessage);
    }
}
