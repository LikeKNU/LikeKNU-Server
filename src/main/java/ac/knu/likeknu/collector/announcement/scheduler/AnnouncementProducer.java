package ac.knu.likeknu.collector.announcement.scheduler;

import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementProducer {

    private final EventProducer<Announcement> producer;

    public AnnouncementProducer(EventProducer<Announcement> producer) {
        this.producer = producer;
    }

    public void produce(Announcement announcement) {
        producer.produce(announcement);
    }

}
