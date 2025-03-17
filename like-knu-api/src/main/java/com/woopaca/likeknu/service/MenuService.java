package com.woopaca.likeknu.service;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.MealType;
import com.woopaca.likeknu.controller.dto.menu.CafeteriaMealListResponse;
import com.woopaca.likeknu.controller.dto.menu.MealListResponse;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.entity.Menu;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.repository.CafeteriaRepository;
import com.woopaca.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    public List<CafeteriaMealListResponse> getCafeteriaMeals(Campus campus, String cafeteriaName) {
        String changedCafeteriaName = changeCafeteriaName(cafeteriaName);

        Cafeteria specifiedCafeteria = cafeteriaRepository.findByCampus(campus)
                .stream()
                .filter(cafeteria -> cafeteria.getCafeteriaName().equals(changedCafeteriaName))
                .findAny()
                .orElseThrow(() -> new BusinessException(
                        String.format("cafeteria name does not exist [%s], on campus [%s]", changedCafeteriaName, campus.getName()))
                );

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        return getPeriodCafeteriaMealList(specifiedCafeteria, startDate, endDate);
    }

    private String changeCafeteriaName(String cafeteriaName) {
        if (cafeteriaName.contains("은행사")) {
            return "홍/은/해";
        }
        if (cafeteriaName.contains("드림")) {
            return "드/비/블";
        }
        return cafeteriaName;
    }

    public List<CafeteriaMealListResponse> getCafeteriaMealsV2(String cafeteriaId) {
        Cafeteria specifiedCafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new BusinessException(
                        String.format("존재하지 않는 cafeteriaId: [%s]", cafeteriaId))
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
