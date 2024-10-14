package ac.knu.likeknu.collector.announcement.scheduler;

import ac.knu.likeknu.collector.announcement.dto.AnnouncementsMessage;
import ac.knu.likeknu.collector.event.EventProducer;
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
