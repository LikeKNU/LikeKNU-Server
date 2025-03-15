package com.woopaca.likeknu.collector.menu.scheduler;

import com.woopaca.likeknu.collector.event.EventProducer;
import com.woopaca.likeknu.collector.menu.dto.Meal;
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
