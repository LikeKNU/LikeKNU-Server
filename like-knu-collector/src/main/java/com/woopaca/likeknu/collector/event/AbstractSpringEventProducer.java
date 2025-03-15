package com.woopaca.likeknu.collector.event;

import org.springframework.context.ApplicationEventPublisher;

public abstract class AbstractSpringEventProducer<T> implements EventProducer<T> {

    private final ApplicationEventPublisher eventPublisher;

    protected AbstractSpringEventProducer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void produce(T event) {
        eventPublisher.publishEvent(event);
    }
}
