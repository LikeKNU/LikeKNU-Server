package ac.knu.likeknu.job.menu.service;

import ac.knu.likeknu.collector.menu.dto.Meal;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.MealType;
import ac.knu.likeknu.job.menu.dto.MealMessage;
import ac.knu.likeknu.job.menu.dto.MenuMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuConsumer {

    private final MenuService menuService;

    @EventListener
    public void consumeMenuMessage(@Valid Meal meal) {
        List<MenuMessage> menus = meal.menus()
                .stream()
                .map(menu -> MenuMessage.of(menu, meal.mealType().name()))
                .toList();
        MealMessage mealMessage = MealMessage.builder()
                .campus(Campus.valueOf(meal.campus().name()))
                .cafeteria(meal.cafeteria())
                .mealType(MealType.valueOf(meal.mealType().name()))
                .menus(menus)
                .build();
        menuService.updateMenus(mealMessage);
    }
}
