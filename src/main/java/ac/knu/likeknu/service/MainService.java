package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Campus;
import ac.knu.likeknu.domain.Tag;
import ac.knu.likeknu.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;

    public ResponseEntity<List<MainAnnouncementsResponse>> getAnnouncementsResponse(Campus campus) {
        List<Campus> campusList = List.of(Campus.ALL, campus);

        Optional<List<Announcement>> getAnnouncementsOptinal =
                announcementRepository
                        .findTop4ByCampusInAndTagOrderByAnnouncementDateDesc(campusList, Tag.SCHOOL_NEWS);

        if(getAnnouncementsOptinal.isEmpty())
            log.info("게시물이 없습니다.");

        return ResponseEntity.ok(
                getAnnouncementsOptinal.get().stream()
                        .map((Announcement a) -> MainAnnouncementsResponse.of(a))
                        .collect(Collectors.toList())
        );
    }

}
