package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
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
public class MainController {

    @GetMapping("/announcements/{campus}")
    public ResponseEntity<List<MainAnnouncementsResponse>> getMainAnnouncements(@PathVariable(name = "campus") String campus) {
        //캠퍼스와 all?

        return null;
    }
}
