package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.menu.MealListDto;
import ac.knu.likeknu.controller.dto.menu.MenuListDto;
import ac.knu.likeknu.controller.dto.menu.MenuResponse;
import ac.knu.likeknu.domain.value.MealType;
import ac.knu.likeknu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DisplayName("메뉴 컨트롤러 테스트")
@WebMvcTest(controllers = MainController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @DisplayName("메뉴 상세조회 API 요청에 성공한다.")
    @Test
    void getMenuResponsesAndSuccess() {
        //given
        MenuResponse menuResponse = new MenuResponse(
                "Test1",
                "TestName1",
                List.of(
                        MealListDto.empty(MealType.BREAKFAST),
                        new MealListDto(
                                "아침",
                                "TestTime",
                                List.of(new MenuListDto(1, "TestMenu1"))
                        )
                )
        );

        //when
        //then
    }
}
