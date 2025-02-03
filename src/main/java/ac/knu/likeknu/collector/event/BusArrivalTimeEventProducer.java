package ac.knu.likeknu.collector.event;

import ac.knu.likeknu.collector.bus.dto.BusArrivalTimes;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BusArrivalTimeEventProducer extends AbstractSpringEventProducer<BusArrivalTimes> {

    protected BusArrivalTimeEventProducer(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
