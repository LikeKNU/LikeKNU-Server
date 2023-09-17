package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/announcements/{campus}")
    public ResponseEntity<List<MainAnnouncementsResponse>> getMainAnnouncements(@PathVariable(name = "campus") String campus) {
        ResponseEntity<List<MainAnnouncementsResponse>> responseEntity = mainService.getAnnouncementsResponse(campus);
        List<MainAnnouncementsResponse> responses = responseEntity.getBody();

        if(responses == null) {
            log.info("response body is null");
            return responseEntity.ofNullable(null);
        }

        return ResponseEntity.ok(responses);
    }
}
