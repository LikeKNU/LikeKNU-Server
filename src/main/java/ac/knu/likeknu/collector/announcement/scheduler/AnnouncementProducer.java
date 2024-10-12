package ac.knu.likeknu.collector.announcement.scheduler;

import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnnouncementProducer {

    private final EventProducer<List<Announcement>> producer;

    public AnnouncementProducer(EventProducer<List<Announcement>> producer) {
        this.producer = producer;
    }

    public void produce(List<Announcement> announcements) {
        producer.produce(announcements);
    }
}
