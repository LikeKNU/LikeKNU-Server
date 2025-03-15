package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.controller.dto.announcement.MainAnnouncementsResponse;
import com.woopaca.likeknu.controller.dto.citybus.MainCityBusResponse;
import com.woopaca.likeknu.controller.dto.menu.MainMenuResponse;
import com.woopaca.likeknu.controller.dto.schedule.MainScheduleResponse;
import com.woopaca.likeknu.entity.AcademicCalendar;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.entity.Menu;
import com.woopaca.likeknu.entity.Route;
import com.woopaca.likeknu.fixture.AcademicCalendarFixture;
import com.woopaca.likeknu.fixture.AnnouncementFixture;
import com.woopaca.likeknu.fixture.CityBusFixture;
import com.woopaca.likeknu.fixture.MealFixture;
import com.woopaca.likeknu.repository.MainHeaderMessageRepository;
import com.woopaca.likeknu.service.CityBusService;
import com.woopaca.likeknu.service.MainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MainController 테스트")
@WithMockUser
@WebMvcTest(controllers = MainController.class,
        excludeFilters = @ComponentScan.Filter(RestControllerAdvice.class))
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MainService mainService;
    @MockBean
    CityBusService cityBusService;
    @MockBean
    MainHeaderMessageRepository mainHeaderMessageRepository;

    @DisplayName("메인 공지사항 목록 조회에 성공한다")
    @Test
    void should_success_when_fetchMainAnnouncements() throws Exception {
        Announcement announcement = AnnouncementFixture.createAnnouncement();
        given(mainService.getAnnouncementsResponse(any(Campus.class)))
                .willReturn(List.of(MainAnnouncementsResponse.of(announcement)));

        mockMvc.perform(get("/api/main/announcements")
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].announcementId").hasJsonPath(),
                        jsonPath("$.data.body[*].announcementTitle").hasJsonPath(),
                        jsonPath("$.data.body[*].announcementUrl").hasJsonPath()
                );
    }

    @DisplayName("메인 시내버스 시간 조회에 성공한다")
    @Test
    void should_success_when_fetchMainCityBuses() throws Exception {
        Route route = CityBusFixture.createRoute();
        CityBus cityBus = CityBusFixture.createCityBus();

        given(cityBusService.earliestArriveCityBuses(any(Campus.class)))
                .willReturn(List.of(MainCityBusResponse.of(route, cityBus)));

        mockMvc.perform(get("/api/main/buses")
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].origin").hasJsonPath(),
                        jsonPath("$.data.body[*].destination").hasJsonPath(),
                        jsonPath("$.data.body[*].busNumber").hasJsonPath(),
                        jsonPath("$.data.body[*].remainingTime").hasJsonPath(),
                        jsonPath("$.data.body[*].busColor").hasJsonPath()
                );
    }

    @DisplayName("메인 식단메뉴 조회에 성공한다")
    @Test
    void should_success_fetchMainMenus() throws Exception {
        Cafeteria cafeteria = MealFixture.createCafeteria();
        Menu menu = MealFixture.createMenuWith(cafeteria);

        given(mainService.getMenuResponse(any(Campus.class)))
                .willReturn(List.of(MainMenuResponse.of(cafeteria, menu.getMenus())));

        mockMvc.perform(get("/api/main/menu")
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].cafeteriaName").hasJsonPath(),
                        jsonPath("$.data.body[*].mealType").hasJsonPath(),
                        jsonPath("$.data.body[*].menus").hasJsonPath()
                );
    }

    @DisplayName("메인 학사일정 조회에 성공한다")
    @Test
    void should_success_fetchMainCalendar() throws Exception {
        AcademicCalendar academicCalendar = AcademicCalendarFixture.createAcademicCalendar();

        given(mainService.getScheduleResponse())
                .willReturn(List.of(MainScheduleResponse.of(academicCalendar)));

        mockMvc.perform(get("/api/main/schedule")
                        .queryParam("campus", Campus.SINGWAN.name()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.body[*].scheduleContents").hasJsonPath(),
                        jsonPath("$.data.body[*].scheduleDate").hasJsonPath(),
                        jsonPath("$.data.body[*].isToday").hasJsonPath()
                );
    }
}