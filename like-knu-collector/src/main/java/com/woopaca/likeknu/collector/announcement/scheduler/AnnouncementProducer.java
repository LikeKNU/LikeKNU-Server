package com.woopaca.likeknu.collector.announcement.scheduler;

import com.woopaca.likeknu.collector.announcement.dto.AnnouncementsMessage;
import com.woopaca.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementProducer {

    private final EventProducer<AnnouncementsMessage> producer;

    public AnnouncementProducer(EventProducer<AnnouncementsMessage> producer) {
        this.producer = producer;
    }

    public void produce(AnnouncementsMessage announcements) {
        producer.produce(announcements);
    }
}
