package ac.knu.likeknu.collector.event;

import ac.knu.likeknu.collector.announcement.dto.AnnouncementsMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementEventProducer extends AbstractSpringEventProducer<AnnouncementsMessage> {

    protected AnnouncementEventProducer(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
