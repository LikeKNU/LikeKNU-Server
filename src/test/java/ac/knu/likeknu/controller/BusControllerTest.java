package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.service.ShuttleBusService;
import ac.knu.likeknu.utils.TestInstanceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("버스 컨트롤러 테스트")
@WebMvcTest(controllers = BusController.class)
class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityBusService cityBusService;
    @MockBean
    private ShuttleBusService shuttleBusService;

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
        ResultActions resultActions = mockMvc.perform(get("/api/buses/city-bus/routes")
                .queryParam("campus", "CHEONAN"));

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].routeId").value(route1.getId()),
                jsonPath("$.data.body.[0].routeName")
                        .value(String.join(" → ", route1.getOrigin(), route1.getDestination())),
                jsonPath("$.data.body.[0].departureStop").value(route1.getDepartureStop()),
                jsonPath("$.data.body.[0].arrivalStop").value(route1.getArrivalStop())
        ).andDo(print());
    }

    @DisplayName("존재하지 않는 캠퍼스의 경우 시내버스 경로 목록 API 요청에 실패한다.")
    @Test
    void fetchEachRouteCityBusesFailCampusNotFound() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/buses/city-bus/routes")
                .queryParam("campus", "invalid_campus"));

        // then
        resultActions.andExpectAll(
                status().isBadRequest()
        ).andDo(print());
    }

    @DisplayName("특정 경로의 시내버스 시간 조회 API 요청에 성공한다")
    @Test
    void fetchCityBusesArrivalTimeSuccess() throws Exception {
        // given
        Route route = TestInstanceFactory.createRoute("Stop A", "Stop B");

        CityBus cityBus1 = TestInstanceFactory.createCityBus("100");
        CityBus cityBus2 = TestInstanceFactory.createCityBus("110");
        CityBus cityBus3 = TestInstanceFactory.createCityBus("120");
        CityBus cityBus4 = TestInstanceFactory.createCityBus("130");

        // when
        LocalTime currentTime = LocalTime.now();
        CityBusesArrivalTimeResponse arrivalTime1 = CityBusesArrivalTimeResponse.of(cityBus1, currentTime, currentTime);
        CityBusesArrivalTimeResponse arrivalTime2 = CityBusesArrivalTimeResponse.of(cityBus2, currentTime.plusMinutes(1), currentTime);
        CityBusesArrivalTimeResponse arrivalTime3 = CityBusesArrivalTimeResponse.of(cityBus3, currentTime.plusMinutes(2), currentTime);
        CityBusesArrivalTimeResponse arrivalTime4 = CityBusesArrivalTimeResponse.of(cityBus4, currentTime.plusMinutes(3), currentTime);
        arrivalTime1.updateArrivalId(1);
        arrivalTime2.updateArrivalId(2);
        arrivalTime3.updateArrivalId(3);
        arrivalTime4.updateArrivalId(4);

        when(cityBusService.getCityBusesArrivalTime(eq(route.getId())))
                .thenReturn(List.of(arrivalTime1, arrivalTime2, arrivalTime3, arrivalTime4));
        ResultActions resultActions = mockMvc.perform(get("/api/buses/city-bus/{routeId}", route.getId()));

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].arrivalId").value(1),
                jsonPath("$.data.body.[0].busNumber").value("100"),
                jsonPath("$.data.body.[0].remainingTime").value("곧 도착"),
                jsonPath("$.data.body.[3].remainingTime").value("3분 뒤"),
                jsonPath("$.data.body.[0].arrivalTime").value(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))),
                jsonPath("$.data.body.[3].arrivalAt").doesNotHaveJsonPath()
        ).andDo(print());
    }
}