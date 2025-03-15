package com.woopaca.likeknu.collector.event;

import com.woopaca.likeknu.collector.menu.dto.Meal;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MealEventProducer extends AbstractSpringEventProducer<Meal> {

    protected MealEventProducer(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
