package ac.knu.likeknu.collector.menu.scheduler;

import ac.knu.likeknu.collector.menu.MenuCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuSchedulingService {

    private final List<MenuCollector> menuCollectors;
    private final MenuProducer menuProducer;

    public MenuSchedulingService(List<MenuCollector> menuCollectors, MenuProducer menuProducer) {
        this.menuCollectors = menuCollectors;
        this.menuProducer = menuProducer;
    }

    @Scheduled(cron = "0 0/10 0-19 * * SUN-WED", zone = "Asia/Seoul")
    public void scheduleMenuProduce() {
        menuCollectors.stream()
                .flatMap(menuCollector -> menuCollector.collectMenus().stream())
                .forEach(menuProducer::produce);
    }
}
