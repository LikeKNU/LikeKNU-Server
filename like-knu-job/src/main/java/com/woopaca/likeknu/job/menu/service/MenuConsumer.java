package com.woopaca.likeknu.job.menu.service;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.collector.menu.dto.Meal;
import com.woopaca.likeknu.job.menu.dto.MealMessage;
import com.woopaca.likeknu.job.menu.dto.MenuMessage;
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
