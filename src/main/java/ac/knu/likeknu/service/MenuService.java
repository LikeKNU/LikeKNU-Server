package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.menu.MealListDto;
import ac.knu.likeknu.controller.dto.menu.MenuResponse;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    public List<MenuResponse> getMenuResponsesByCampus(Campus campus) {
        List<Cafeteria> cafeterias = cafeteriaRepository.findByCampus(campus);

        return cafeterias.stream()
                .sorted(Comparator.comparing(cafeteria -> cafeteria.getCafeteriaName().getSequence()))
                .map(cafeteria -> {
                    List<MealListDto> mealLists = Arrays.stream(MealType.values())
                            .filter(mealType -> !isMealTypeDawnOrNight(mealType))
                            .map(mealType ->
                                    menuRepository.findByMenuDateAndCafeteriaAndMealType(LocalDate.now(), cafeteria, mealType)
                                            .map(menu -> MealListDto.of(mealType, cafeteria, menu.getMenus()))
                                            .orElse(MealListDto.empty(mealType))
                            ).collect(Collectors.toList());
                    return MenuResponse.of(cafeteria, mealLists);
                }).collect(Collectors.toList());
    }

    private boolean isMealTypeDawnOrNight(MealType mealType) {
        return mealType.equals(MealType.DAWN) || mealType.equals(MealType.NIGHT);
    }
}
