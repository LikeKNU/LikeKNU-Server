package ac.knu.likeknu.collector.menu.scheduler;

import ac.knu.likeknu.collector.event.EventProducer;
import ac.knu.likeknu.collector.menu.dto.Meal;
import org.springframework.stereotype.Component;

@Component
public class MenuProducer {

    private final EventProducer<Meal> producer;

    public MenuProducer(EventProducer<Meal> producer) {
        this.producer = producer;
    }

    public void produce(Meal meal) {
        producer.produce(meal);
    }
}
