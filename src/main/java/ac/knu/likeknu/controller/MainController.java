package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.*;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final CityBusService cityBusService;

    @GetMapping("/announcements")
    public ResponseDto<List<MainAnnouncementsResponse>> getMainAnnouncements(@RequestParam(name = "campus") Campus campus) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<MainAnnouncementsResponse> responses = mainService.getAnnouncementsResponse(campus);
        return ResponseDto.of(responses);
    }

    @GetMapping("/menu")
    public ResponseDto<List<MainMenuResponse>> getMainMenu(@RequestParam(name = "campus") Campus campus) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<MainMenuResponse> responses = mainService.getMenuResponse(campus);
        return ResponseDto.of(responses);
    }

    @GetMapping("/buses")

    public ResponseDto<List<MainCityBusResponse>> getMainPageCityBuses(
            @RequestParam(name = "campus") Campus campus
    ) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<MainCityBusResponse> cityBuses = cityBusService.earliestOutgoingCityBuses(campus);
        return ResponseDto.of(cityBuses);
    }

    @GetMapping("/schedule")
    public ResponseDto<List<MainScheduleResponse>> getMainSchedule() {
        return ResponseDto.of(null);
    }
}
