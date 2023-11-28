package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.menu.MenuResponse;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.CafeteriaName;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("식단 비즈니스 로직 테스트")
@ExtendWith(value = MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MenuServiceTest {

    @InjectMocks
    MenuService menuService;

    @Mock
    CafeteriaRepository cafeteriaRepository;
    @Mock
    MenuRepository menuRepository;

    @Test
    void getMenuDataAndSuccess() {
        //given
        Cafeteria cafeteria1 = new Cafeteria(CafeteriaName.STUDENT_CAFETERIA, null, "TestTime1", null, null, null, null, Campus.CHEONAN);
        Cafeteria cafeteria2 = new Cafeteria(CafeteriaName.EMPLOYEE_CAFETERIA, null, "TestTime2", null, null, null, null, Campus.CHEONAN);
        Cafeteria cafeteria3 = new Cafeteria(CafeteriaName.STUDENT_CAFETERIA, null, "TestTime3", "TestTime4", null, null, null, Campus.SINGWAN);
        Cafeteria cafeteria4 = new Cafeteria(CafeteriaName.EMPLOYEE_CAFETERIA, "TestTime9", "TestTime5", "TestTime6", null, "TestTime7", null, Campus.SINGWAN);

        Menu menu1 = new Menu("menu1 menu2 menu3", MealType.LUNCH, LocalDate.now(), cafeteria1);
        Menu menu2 = new Menu("menu4 menu5 menu6", MealType.LUNCH, LocalDate.now(), cafeteria2);
        Menu menu3 = new Menu("menu7 menu8, menu9 menu10", MealType.DINNER, LocalDate.now(), cafeteria3);
        Menu menu4 = new Menu("menu11 menu12, menu13 menu14", MealType.DINNER, LocalDate.now(), cafeteria4);
        Menu menu5 = new Menu("menu15 menu16", MealType.LUNCH, LocalDate.now(), cafeteria4);

        when(cafeteriaRepository.findByCampus(eq(Campus.CHEONAN))).thenReturn(List.of(cafeteria1, cafeteria2));
        when(menuRepository.findByMenuDateAndCafeteriaAndMealType(eq(LocalDate.now()), eq(cafeteria1), eq(MealType.LUNCH))).thenReturn(Optional.of(menu1));
        when(menuRepository.findByMenuDateAndCafeteriaAndMealType(eq(LocalDate.now()), eq(cafeteria2), eq(MealType.LUNCH))).thenReturn(Optional.of(menu2));
        List<MenuResponse> menuResponsesByCheonan = menuService.getMenuResponsesByCampus(Campus.CHEONAN, LocalDate.now());

        when(cafeteriaRepository.findByCampus(eq(Campus.SINGWAN))).thenReturn(List.of(cafeteria3, cafeteria4));
        when(menuRepository.findByMenuDateAndCafeteriaAndMealType(eq(LocalDate.now()), eq(cafeteria3), eq(MealType.DINNER))).thenReturn(Optional.of(menu3));
        when(menuRepository.findByMenuDateAndCafeteriaAndMealType(eq(LocalDate.now()), eq(cafeteria4), eq(MealType.DINNER))).thenReturn(Optional.of(menu4));
        when(menuRepository.findByMenuDateAndCafeteriaAndMealType(eq(LocalDate.now()), eq(cafeteria4), eq(MealType.LUNCH))).thenReturn(Optional.of(menu5));
        List<MenuResponse> menuResponsesBySingwan = menuService.getMenuResponsesByCampus(Campus.SINGWAN, LocalDate.now());

        //then
        assertAll(
                () -> assertThat(menuResponsesByCheonan.get(0).getCafeteriaName()).isEqualTo(menu1.getCafeteria().getCafeteriaName().getCafeteriaName()),
                () -> assertThat(menuResponsesByCheonan.get(1).getToday().get(0).getMenus()).isEqualTo(new ArrayList<>()),
                () -> assertThat(menuResponsesByCheonan.get(1).getCafeteriaName()).isEqualTo(menu2.getCafeteria().getCafeteriaName().getCafeteriaName()),
                () -> assertThat(menuResponsesByCheonan.get(1).getToday().get(1).getMenus().get(0).getMenuName()).isEqualTo("menu4"),

                () -> assertThat(menuResponsesBySingwan.get(0).getCafeteriaName()).isEqualTo(menu3.getCafeteria().getCafeteriaName().getCafeteriaName()),
                () -> assertThat(menuResponsesBySingwan.get(0).getToday().get(0).getMenus()).isEqualTo(new ArrayList<>()),
                () -> assertThat(menuResponsesBySingwan.get(1).getCafeteriaName()).isEqualTo(menu4.getCafeteria().getCafeteriaName().getCafeteriaName()),
                () -> assertThat(menuResponsesBySingwan.get(1).getToday().get(1).getMenus().get(0).getMenuName()).isEqualTo("menu15")
        );


    }
}
