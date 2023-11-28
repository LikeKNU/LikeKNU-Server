package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.menu.MealListDto;
import ac.knu.likeknu.controller.dto.menu.MenuListDto;
import ac.knu.likeknu.controller.dto.menu.MenuResponse;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.MealType;
import ac.knu.likeknu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DisplayName("메뉴 컨트롤러 테스트")
@WebMvcTest(controllers = MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @DisplayName("캠퍼스별 메뉴 상세조회 API 요청에 성공한다.")
    @Test
    void getMenuResponsesAndSuccess() throws Exception {
        //given
        LocalDate localDate = LocalDate.of(2023, 12, 11);
        LocalDate localDate2 = LocalDate.of(2023, 12, 12);


        List<MenuListDto> menuListDtos = List.of(
                new MenuListDto(1, "TestMenu1"),
                new MenuListDto(2, "TestMenu2"),
                new MenuListDto(3, "TestMenu3")
        );

        MenuResponse menuResponse1 = new MenuResponse(
                "Test1",
                "TestName1",
                List.of(
                        MealListDto.empty(MealType.BREAKFAST, localDate),
                        new MealListDto("점심", "TestTime1", menuListDtos, localDate),
                        new MealListDto("저녁", "TestTime2", menuListDtos, localDate)
                ),
                List.of(
                        MealListDto.empty(MealType.BREAKFAST, localDate2),
                        new MealListDto("점심", "TestTime1", menuListDtos, localDate2),
                        new MealListDto("저녁", "TestTime2", menuListDtos, localDate2)
                )
        );

        MenuResponse menuResponse2 = new MenuResponse(
                "Test2",
                "TestName2",
                List.of(
                        MealListDto.empty(MealType.BREAKFAST, localDate),
                        MealListDto.empty(MealType.LUNCH, localDate),
                        MealListDto.empty(MealType.DINNER, localDate)
                ),
                List.of(
                        MealListDto.empty(MealType.BREAKFAST, localDate2),
                        MealListDto.empty(MealType.LUNCH, localDate2),
                        MealListDto.empty(MealType.DINNER, localDate2)
                )
        );

        MenuResponse menuResponse3 = new MenuResponse(
                "Test3",
                "TestName3",
                List.of(
                        new MealListDto("아침", "TestTime3", menuListDtos, localDate),
                        new MealListDto("점심", "TestTime4", menuListDtos, localDate),
                        new MealListDto("저녁", "TestTime5", menuListDtos, localDate)
                ),
                List.of(
                        new MealListDto("아침", "TestTime3", menuListDtos, localDate2),
                        new MealListDto("점심", "TestTime4", menuListDtos, localDate2),
                        new MealListDto("저녁", "TestTime5", menuListDtos, localDate2)
                )
        );

        MenuResponse menuResponse4 = new MenuResponse(
                "Test3",
                "TestName3",
                List.of(
                        new MealListDto("아침", "TestTime6", menuListDtos, localDate),
                        new MealListDto("점심", "TestTime7", menuListDtos, localDate),
                        new MealListDto("저녁", "TestTime8", menuListDtos, localDate)
                ),
                List.of(
                        new MealListDto("아침", "TestTime6", menuListDtos, localDate2),
                        new MealListDto("점심", "TestTime7", menuListDtos, localDate2),
                        new MealListDto("저녁", "TestTime8", menuListDtos, localDate2)
                )
        );

        List<MenuResponse> menuResponseList1 = List.of(menuResponse1, menuResponse2, menuResponse4);
        List<MenuResponse> menuResponseList2 = List.of(menuResponse1, menuResponse3, menuResponse4);

        //when
        when(menuService.getMenuResponsesByCampus(eq(Campus.CHEONAN), eq(localDate)))
                .thenReturn(menuResponseList1);
        when(menuService.getMenuResponsesByCampus(eq(Campus.SINGWAN), eq(localDate2)))
                .thenReturn(menuResponseList2);

        ResultActions resultActions1 = mockMvc.perform(
                get("/api/menu")
                        .param("campus", Campus.CHEONAN.name())
                        .param("date", localDate.toString())
        );
        ResultActions resultActions2 = mockMvc.perform(
                get("/api/menu")
                        .param("campus", Campus.SINGWAN.name())
                        .param("date", localDate2.toString())
        );

        //then
        resultActions1.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].cafeteriaId").value(menuResponse1.getCafeteriaId()),
                jsonPath("$.data.body.[1].cafeteriaName").value(menuResponse2.getCafeteriaName()),
                jsonPath("$.data.body.[2].today.[0].mealType").value(menuResponse4.getToday().get(0).getMealType()),
                jsonPath("$.data.body.[2].today.[1].operatingTime").value(menuResponse4.getToday().get(1).getOperatingTime()),
                jsonPath("$.data.body.[1].today.[2].menus").value(menuResponse2.getToday().get(2).getMenus()),
                jsonPath("$.data.body.[2].today.[2].menus.[1].menuId").value(menuResponse4.getToday().get(2).getMenus().get(1).getMenuId()),
                jsonPath("$.data.body.[2].tomorrow.[2].menus.[2].menuName").value(menuResponse4.getTomorrow().get(2).getMenus().get(2).getMenuName())
        ).andDo(print());

        resultActions2.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].cafeteriaId").value(menuResponse1.getCafeteriaId()),
                jsonPath("$.data.body.[1].cafeteriaName").value(menuResponse3.getCafeteriaName()),
                jsonPath("$.data.body.[2].today.[0].mealType").value(menuResponse4.getToday().get(0).getMealType()),
                jsonPath("$.data.body.[2].tomorrow.[1].operatingTime").value(menuResponse4.getTomorrow().get(1).getOperatingTime()),
                jsonPath("$.data.body.[1].today.[2].menus.[0].menuName").value(menuResponse3.getToday().get(2).getMenus().get(0).getMenuName()),
                jsonPath("$.data.body.[2].tomorrow.[2].menus.[1].menuId").value(menuResponse4.getTomorrow().get(2).getMenus().get(1).getMenuId()),
                jsonPath("$.data.body.[2].today.[2].menus.[2].menuName").value(menuResponse4.getToday().get(2).getMenus().get(2).getMenuName())
        ).andDo(print());
    }
}
