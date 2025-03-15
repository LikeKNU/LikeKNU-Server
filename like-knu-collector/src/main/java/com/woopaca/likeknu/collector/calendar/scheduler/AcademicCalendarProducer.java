package com.woopaca.likeknu.collector.calendar.scheduler;

import com.woopaca.likeknu.collector.calendar.dto.CalendarsMessage;
import com.woopaca.likeknu.collector.event.EventProducer;
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
