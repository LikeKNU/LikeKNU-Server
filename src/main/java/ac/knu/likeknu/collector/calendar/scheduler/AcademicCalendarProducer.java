package ac.knu.likeknu.collector.calendar.scheduler;

import ac.knu.likeknu.collector.calendar.dto.AcademicCalendar;
import ac.knu.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

@Component
public class AcademicCalendarProducer {

    private final EventProducer<AcademicCalendar> producer;

    public AcademicCalendarProducer(EventProducer<AcademicCalendar> producer) {
        this.producer = producer;
    }

    public void produce(AcademicCalendar academicCalendar) {
        producer.produce(academicCalendar);
    }
}
