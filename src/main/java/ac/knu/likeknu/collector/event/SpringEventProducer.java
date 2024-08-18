package ac.knu.likeknu.collector.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventProducer<T> implements EventProducer<T> {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventProducer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void produce(T event) {
        eventPublisher.publishEvent(event);
    }
}
