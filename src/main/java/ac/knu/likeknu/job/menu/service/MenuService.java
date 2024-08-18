package ac.knu.likeknu.job.menu.service;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.CafeteriaKey;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.job.menu.dto.MealMessage;
import ac.knu.likeknu.job.menu.dto.MenuMessage;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service("jobMenuService")
public class MenuService {

    private final Map<CafeteriaKey, Set<MenuMessage>> menuCache = new ConcurrentHashMap<>();

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    @PostConstruct
    void init() {
        LocalDate thisSunday = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        cafeteriaRepository.findAll()
                .forEach(cafeteria -> {
                    menuCache.put(CafeteriaKey.from(cafeteria), Collections.synchronizedSet(new HashSet<>()));
                    menuRepository.findByMenuDateAfterAndCafeteria(thisSunday.minusDays(1), cafeteria)
                            .forEach(menu -> caching(MealMessage.of(cafeteria), MenuMessage.from(menu)));
                });
    }

    public void updateMenus(MealMessage mealMessage) {
        log.debug("updateMenus = {}", mealMessage);
        mealMessage.menus()
                .stream()
                .filter(menuMessage -> !isAlreadyCollected(mealMessage, menuMessage))
                .forEach(menuMessage -> updateMenu(mealMessage, menuMessage));
    }

    private boolean isAlreadyCollected(MealMessage mealMessage, MenuMessage menuMessage) {
        return menuCache.get(CafeteriaKey.from(mealMessage))
                .contains(menuMessage);
    }

    private void updateMenu(MealMessage mealMessage, MenuMessage menuMessage) {
        caching(mealMessage, menuMessage);

        Campus campus = mealMessage.campus();
        String cafeteriaName = mealMessage.cafeteria();
        Cafeteria cafeteria = cafeteriaRepository.findByCampusAndCafeteriaName(campus, cafeteriaName)
                .orElseThrow();
        menuRepository.findByCafeteriaAndMenuDateAndMealType(cafeteria, menuMessage.date(), mealMessage.mealType())
                .ifPresentOrElse(menu -> menu.updateMenus(menuMessage.menu()),
                        () -> menuRepository.save(
                                Menu.builder()
                                        .menus(menuMessage.menu())
                                        .menuDate(menuMessage.date())
                                        .mealType(mealMessage.mealType())
                                        .cafeteria(cafeteria)
                                        .build()
                        ));
    }

    private void caching(MealMessage mealMessage, MenuMessage menuMessage) {
        log.debug("caching menuMessage = {}", menuMessage);
        Set<MenuMessage> menuMessages = menuCache.get(new CafeteriaKey(mealMessage.campus(), mealMessage.cafeteria()));
        menuMessages.add(menuMessage);
    }

    @Scheduled(cron = "0 0 23 * * SAT")
    public void scheduledMenuCache() {
        menuCache.values()
                .forEach(Set::clear);
    }
}
