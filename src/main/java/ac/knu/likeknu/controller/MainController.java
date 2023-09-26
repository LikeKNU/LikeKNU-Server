package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.controller.dto.response.MainCityBusResponse;
import ac.knu.likeknu.controller.dto.response.MainMenuResponse;
import ac.knu.likeknu.controller.dto.response.ResponseDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final CityBusService cityBusService;

    @GetMapping("/announcements")
    public ResponseDto<List<MainAnnouncementsResponse>> getMainAnnouncements(@RequestParam(name = "campus") String campus) {
        List<MainAnnouncementsResponse> responses = mainService.getAnnouncementsResponse(Campus.of(campus));
        return ResponseDto.of(responses);
    }

    @GetMapping("/menu")
    public ResponseDto<List<MainMenuResponse>> getMainMenu(@RequestParam(name = "campus") String campus) {
        List<MainMenuResponse> responses = mainService.getMenuResponse(Campus.of(campus));

        return ResponseDto.of(responses);
    }

    @GetMapping("/buses")
    public ResponseDto<List<MainCityBusResponse>> getMainPageCityBuses(@RequestParam(name = "campus") String campus) {
        List<MainCityBusResponse> cityBuses = cityBusService.earliestOutgoingCityBuses(Campus.of(campus));
        return ResponseDto.of(cityBuses);
    }
}
