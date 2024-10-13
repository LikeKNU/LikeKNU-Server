package ac.knu.likeknu.collector.menu.scheduler;

import ac.knu.likeknu.collector.menu.MenuCollector;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MenuSchedulingService {

    private final List<MenuCollector> menuCollectors;
    private final MenuProducer menuProducer;

    public MenuSchedulingService(List<MenuCollector> menuCollectors, MenuProducer menuProducer) {
        this.menuCollectors = menuCollectors;
        this.menuProducer = menuProducer;
    }

    @Scheduled(cron = "50 0/10 9-18 * * *", zone = "Asia/Seoul")
    public void scheduleMenuProduce() {
        menuCollectors.stream()
                .map(MenuCollector::collectMenus)
                .flatMap(Collection::stream)
                .forEach(menuProducer::produce);
    }
}
