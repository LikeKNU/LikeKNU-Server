package ac.knu.likeknu.job.menu.service;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.job.menu.MenuCache;
import ac.knu.likeknu.job.menu.dto.MealMessage;
import ac.knu.likeknu.job.menu.dto.MenuMessage;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service("jobMenuService")
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;
    private final MenuCache menuCache;

    public void updateMenus(MealMessage mealMessage) {
        mealMessage.menus()
                .stream()
                .filter(menuMessage -> menuCache.isAbsent(mealMessage, menuMessage))
                .map(menuMessage -> menuCache.cache(mealMessage, menuMessage))
                .forEach(menuMessage -> {
                    appendMenu(mealMessage, menuMessage);
                });
    }

    private void appendMenu(MealMessage mealMessage, MenuMessage menuMessage) {
        Campus campus = mealMessage.campus();
        String cafeteriaName = mealMessage.cafeteria();
        Cafeteria cafeteria = cafeteriaRepository.findByCampusAndCafeteriaName(campus, cafeteriaName)
                .orElseThrow();
        menuRepository.findByCafeteriaAndMenuDateAndMealType(cafeteria, menuMessage.date(), mealMessage.mealType())
                .ifPresentOrElse(
                        menu -> menu.updateMenus(menuMessage.menu()),
                        () -> menuRepository.save(
                                Menu.builder()
                                        .menus(menuMessage.menu())
                                        .menuDate(menuMessage.date())
                                        .mealType(mealMessage.mealType())
                                        .cafeteria(cafeteria)
                                        .build()
                        ));
    }
}
