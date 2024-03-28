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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    public List<MenuResponse> getMenuResponsesByCampus(Campus campus, LocalDate date) {
        List<Cafeteria> cafeterias = cafeteriaRepository.findByCampus(campus);

        return cafeterias.stream()
                .sorted(Comparator.comparing(Cafeteria::getSequence))
                .map(cafeteria -> MenuResponse.of(cafeteria, createMapContainingMealListDto(cafeteria, date)))
                .collect(Collectors.toList());
    }

    private Map<LocalDate, List<MealListDto>> createMapContainingMealListDto(Cafeteria cafeteria, LocalDate date) {
        return Arrays.stream(MealType.values())
                .flatMap(mealType -> Stream.of(date, date.plusDays(1))
                        .map(day -> findRepositoryAndMapDto(mealType, cafeteria, day)))
                .collect(
                        Collectors.groupingBy(MealListDto::getDate,
                                Collectors.mapping(mealListDto -> mealListDto, Collectors.toList())
                        ));
    }

    private MealListDto findRepositoryAndMapDto(MealType mealType, Cafeteria cafeteria, LocalDate date) {
        return menuRepository.findByMenuDateAndCafeteriaAndMealType(date, cafeteria, mealType)
                .map(menu -> MealListDto.of(mealType, cafeteria, menu))
                .orElse(MealListDto.empty(mealType, cafeteria, date));
    }
}
