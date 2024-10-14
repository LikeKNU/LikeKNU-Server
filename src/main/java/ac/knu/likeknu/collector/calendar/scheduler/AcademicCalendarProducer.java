package ac.knu.likeknu.collector.calendar.scheduler;

import ac.knu.likeknu.collector.calendar.dto.CalendarsMessage;
import ac.knu.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

@Component
public class AcademicCalendarProducer {

    private final EventProducer<CalendarsMessage> producer;

    public AcademicCalendarProducer(EventProducer<CalendarsMessage> producer) {
        this.producer = producer;
    }

    public void produce(CalendarsMessage calendarsMessage) {
        producer.produce(calendarsMessage);
    }
}
