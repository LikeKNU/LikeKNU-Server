package com.woopaca.likeknu.collector.menu.scheduler;

import com.woopaca.likeknu.collector.menu.MenuCollector;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MenuSchedulingService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<MenuCollector> menuCollectors;

    public MenuSchedulingService(List<MenuCollector> menuCollectors, ApplicationEventPublisher applicationEventPublisher) {
        this.menuCollectors = menuCollectors;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "50 0/10 9-18 * * *")
    public void scheduleMenuProduce() {
        menuCollectors.stream()
                .map(MenuCollector::collectMenus)
                .flatMap(Collection::stream)
                .forEach(applicationEventPublisher::publishEvent);
    }
}
