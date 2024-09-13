package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.menu.CafeteriaMealListResponse;
import ac.knu.likeknu.controller.dto.menu.MealListResponse;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.MealType;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    public List<CafeteriaMealListResponse> getCafeteriaMeals(Campus campus, String cafeteriaName) {
        Cafeteria specifiedCafeteria = cafeteriaRepository.findByCampus(campus)
                .stream()
                .filter(cafeteria -> cafeteria.getCafeteriaName().equals(cafeteriaName))
                .findAny()
                .orElseThrow(() -> new BusinessException(
                        String.format("cafeteria name does not exist [%s], on campus [%s]", cafeteriaName, campus.getName()))
                );

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        return getPeriodCafeteriaMealList(specifiedCafeteria, startDate, endDate);
    }

    private List<CafeteriaMealListResponse> getPeriodCafeteriaMealList(Cafeteria specifiedCafeteria, LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, date -> date.isBefore(endDate.plusDays(1)),
                        date -> date.plusDays(1))
                .map(date -> getOneDayCafeteriaMealList(specifiedCafeteria, date))
                .toList();
    }

    private CafeteriaMealListResponse getOneDayCafeteriaMealList(Cafeteria cafeteria, LocalDate date) {
        List<Menu> menus = menuRepository.findByCafeteriaAndMenuDate(cafeteria, date);
        List<MealListResponse> mealList = Arrays.stream(MealType.values())
                .filter(mealType -> cafeteria.isOperate(mealType, date))
                .map(mealType -> mealFiltering(menus, mealType)
                        .map(menu -> MealListResponse.of(menu, cafeteria))
                        .orElse(MealListResponse.empty(mealType, cafeteria.getOperatingTime(mealType, date))))
                .toList();
        return CafeteriaMealListResponse.of(cafeteria, date, mealList);
    }

    private Optional<Menu> mealFiltering(List<Menu> menus, MealType mealType) {
        return menus.stream()
                .filter(menu -> menu.getMealType().equals(mealType))
                .findAny();
    }
}
