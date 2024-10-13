package ac.knu.likeknu.job.menu;

import ac.knu.likeknu.domain.CafeteriaKey;
import ac.knu.likeknu.job.menu.dto.MealMessage;
import ac.knu.likeknu.job.menu.dto.MenuMessage;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import ac.knu.likeknu.utils.DateTimeUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MenuCache {

    private final Map<CafeteriaKey, Set<MenuMessage>> menuCache = new ConcurrentHashMap<>();

    private final CafeteriaRepository cafeteriaRepository;
    private final MenuRepository menuRepository;

    public MenuCache(CafeteriaRepository cafeteriaRepository, MenuRepository menuRepository) {
        this.cafeteriaRepository = cafeteriaRepository;
        this.menuRepository = menuRepository;
    }

    @PostConstruct
    private void loadCache() {
        LocalDate previousSunday = DateTimeUtils.getPreviousOrSameDate(DayOfWeek.SUNDAY);
        cafeteriaRepository.findAll()
                .forEach(cafeteria -> {
                    menuCache.put(CafeteriaKey.from(cafeteria), Collections.synchronizedSet(new HashSet<>()));
                    menuRepository.findByMenuDateAfterAndCafeteria(previousSunday.minusDays(1), cafeteria)
                            .forEach(menu -> cache(MealMessage.of(cafeteria), MenuMessage.from(menu)));
                });
    }

    public MenuMessage cache(MealMessage mealMessage, MenuMessage menuMessage) {
        Set<MenuMessage> menuMessages = menuCache.get(new CafeteriaKey(mealMessage.campus(), mealMessage.cafeteria()));
        menuMessages.add(menuMessage);
        return menuMessage;
    }

    public boolean isAbsent(MealMessage mealMessage, MenuMessage menuMessage) {
        Set<MenuMessage> menuMessages = menuCache.get(new CafeteriaKey(mealMessage.campus(), mealMessage.cafeteria()));
        return !menuMessages.contains(menuMessage);
    }

    @Scheduled(cron = "0 0 0 * * MON")
    private void scheduledMenuCache() {
        LocalDate currentDate = LocalDate.now();
        menuCache.values()
                .forEach(menuMessages -> menuMessages.removeIf(menuMessage -> menuMessage.date().isBefore(currentDate)));
    }
}
