package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Campus;
import ac.knu.likeknu.domain.Tag;
import ac.knu.likeknu.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;

    public ResponseEntity<List<MainAnnouncementsResponse>> getAnnouncementsResponse(Campus campus) {
        List<Campus> campusList = new ArrayList<>();
        campusList.add(Campus.ALL);
        campusList.add(campus);

        List<Announcement> getAnnouncements =
                announcementRepository
                        .findTop4ByCampusInAndTagOrderByAnnouncementDateDesc(campusList, Tag.SCHOOL_NEWS)
                        .orElse(null);

        if(getAnnouncements == null)
            throw new NullPointerException("null 값이 반환되었습니다.");

        return ResponseEntity.ok(
                getAnnouncements.stream()
                        .map((Announcement a) -> MainAnnouncementsResponse.of(a))
                        .collect(Collectors.toList())
        );
    }

}
