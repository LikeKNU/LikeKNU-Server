package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.utils.TestInstanceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("시내버스 컨트롤러 테스트")
@WebMvcTest(controllers = CityBusController.class)
class CityBusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityBusService cityBusService;

    @DisplayName("캠퍼스별 시내버스 경로 목록 조회 API 요청에 성공한다.")
    @Test
    void fetchEachRouteCityBusesSuccess() throws Exception {
        // given
        Route route1 = TestInstanceFactory
                .createRoute("Stop A", "Stop B", "A", "B");
        Route route2 = TestInstanceFactory
                .createRoute("Stop A", "Stop C", "A", "C");
        Route route3 = TestInstanceFactory
                .createRoute("Stop D", "Stop A", "A", "B");

        // when
        when(cityBusService.getRouteList(eq(Campus.CHEONAN))).thenReturn(List.of(
                RouteListResponse.of(route1),
                RouteListResponse.of(route2),
                RouteListResponse.of(route3)
        ));
        ResultActions resultActions = mockMvc.perform(get("/api/city-buses/routes")
                .queryParam("campus", "CHEONAN"));

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].routeId").value(route1.getId()),
                jsonPath("$.data.body.[0].origin").value(route1.getOrigin()),
                jsonPath("$.data.body.[0].destination").value(route1.getDestination())
        ).andDo(print());
    }

    @DisplayName("존재하지 않는 캠퍼스의 경우 시내버스 경로 목록 API 요청에 실패한다.")
    @Test
    void fetchEachRouteCityBusesFailCampusNotFound() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/city-buses/routes")
                .queryParam("campus", "invalid_campus"));

        // then
        resultActions.andExpectAll(
                status().isBadRequest()
        ).andDo(print());
    }
}