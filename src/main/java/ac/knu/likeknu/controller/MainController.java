package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.controller.dto.response.MainMenuResponse;
import ac.knu.likeknu.controller.dto.response.ResponseDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/announcements")
    public ResponseDto<List<MainAnnouncementsResponse>> getMainAnnouncements(@RequestParam(name = "campus") String campus) {

        ResponseEntity<List<MainAnnouncementsResponse>> responseEntity = mainService.getAnnouncementsResponse(Campus.of(campus));
        List<MainAnnouncementsResponse> responses = responseEntity.getBody();

        return ResponseDto.of(responses);
    }

    @GetMapping("/menu")
    public ResponseDto<List<MainMenuResponse>> getMainMenu(@RequestParam(name = "campus") String campus) {
        List<MainMenuResponse> responses = mainService.getMenuResponse(Campus.of(campus));

        return ResponseDto.of(responses);
    }
}
