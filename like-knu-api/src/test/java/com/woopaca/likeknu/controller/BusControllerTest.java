package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.RouteType;
import com.woopaca.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import com.woopaca.likeknu.controller.dto.citybus.CityBusesResponse;
import com.woopaca.likeknu.controller.dto.shuttlebus.ShuttleBusesArrivalTimeResponse;
import com.woopaca.likeknu.controller.dto.shuttlebus.ShuttleListResponse;
import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.entity.Route;
import com.woopaca.likeknu.entity.Shuttle;
import com.woopaca.likeknu.entity.ShuttleBus;
import com.woopaca.likeknu.entity.ShuttleTime;
import com.woopaca.likeknu.fixture.CityBusFixture;
import com.woopaca.likeknu.fixture.ShuttleBusFixture;
import com.woopaca.likeknu.service.CityBusService;
import com.woopaca.likeknu.service.ShuttleBusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BusController 테스트")
@WithMockUser
@WebMvcTest(controllers = BusController.class,
        excludeFilters = @ComponentScan.Filter(RestControllerAdvice.class))
class BusControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CityBusService cityBusService;
    @MockBean
    ShuttleBusService shuttleBusService;

    @DisplayName("시내버스 도착 시간 조회에 성공한다")
    @Test
    void should_success_fetchCityBusArrivalTimes() throws Exception {
        Route route = CityBusFixture.createRoute();
        CityBus cityBus = CityBusFixture.createCityBus();
        LocalTime currentTime = LocalTime.now();

        given(cityBusService.getCityBusesArrivalTime(any(Campus.class), any(RouteType.class)))
                .willReturn(List.of(
                        CityBusesResponse.of(route, cityBus.getArrivalTimes()
                                .stream()
                                .map(arrivalTime -> CityBusesArrivalTimeResponse.of(cityBus, arrivalTime, currentTime))
                                .toList())
                ));

        mockMvc.perform(get("/api/buses/city-bus/{type}", RouteType.INCOMING.name().toLowerCase())
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].origin").hasJsonPath(),
                        jsonPath("$.data.body[*].destination").hasJsonPath(),
                        jsonPath("$.data.body[*].departureStop").hasJsonPath(),
                        jsonPath("$.data.body[*].buses").hasJsonPath(),
                        jsonPath("$.data.body[*].buses[*].busNumber").hasJsonPath(),
                        jsonPath("$.data.body[*].buses[*].remainingTime").hasJsonPath(),
                        jsonPath("$.data.body[*].buses[*].busColor").hasJsonPath()
                );
    }

    @DisplayName("셔틀 목록 조회에 성공한다")
    @Test
    void should_success_fetchShuttles() throws Exception {
        Shuttle shuttle = ShuttleBusFixture.createShuttle();

        given(shuttleBusService.getRouteList(any(Campus.class)))
                .willReturn(List.of(ShuttleListResponse.of(shuttle, "next")));

        mockMvc.perform(get("/api/buses/shuttle-bus/routes")
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].shuttleId").hasJsonPath(),
                        jsonPath("$.data.body[*].shuttleName").hasJsonPath(),
                        jsonPath("$.data.body[*].note").hasJsonPath(),
                        jsonPath("$.data.body[*].nextDepartureTime").hasJsonPath()
                );
    }

    @DisplayName("셔틀버스 시간 조회에 성공한다")
    @Test
    void should_success_fetchShuttleBusTimes() throws Exception {
        List<ShuttleTime> shuttleTimes = ShuttleBusFixture.createShuttleTimes(3, Duration.ofMinutes(5));
        ShuttleBus shuttleBus = ShuttleBusFixture.createShuttleBusWithTimes(shuttleTimes);

        given(shuttleBusService.getShuttleBusesArrivalTime(any(String.class)))
                .willReturn(List.of(ShuttleBusesArrivalTimeResponse.of(shuttleBus)));

        mockMvc.perform(get("/api/buses/shuttle-bus/{shuttleId}", "shuttle_test"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].shuttleBusId").hasJsonPath(),
                        jsonPath("$.data.body[*].busName").hasJsonPath(),
                        jsonPath("$.data.body[*].isRunning").hasJsonPath(),
                        jsonPath("$.data.body[*].times").hasJsonPath(),
                        jsonPath("$.data.body[*].times[*].arrivalStop").hasJsonPath(),
                        jsonPath("$.data.body[*].times[*].arrivalTime").hasJsonPath()
                );
    }
}