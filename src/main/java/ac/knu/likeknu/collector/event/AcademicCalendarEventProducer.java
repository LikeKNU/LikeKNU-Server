package ac.knu.likeknu.collector.event;

import ac.knu.likeknu.collector.calendar.dto.CalendarsMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AcademicCalendarEventProducer extends AbstractSpringEventProducer<CalendarsMessage> {

    protected AcademicCalendarEventProducer(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
