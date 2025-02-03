package ac.knu.likeknu.collector.event;

import ac.knu.likeknu.collector.menu.dto.Meal;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MealEventProducer extends AbstractSpringEventProducer<Meal> {

    protected MealEventProducer(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
