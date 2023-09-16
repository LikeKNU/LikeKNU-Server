package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private static final String TAG = "학생소식";

    private final AnnouncementRepository announcementRepository;

    public ResponseEntity<List<MainAnnouncementsResponse>> getAnnouncementsResponse(String campus) {
        List<String> campusList = new ArrayList<>();
        campusList.add(campus);
        campusList.add("ALL");

        List<Announcement> getAnnouncements =
                announcementRepository
                        .findTop5ByCampusInAndTagOrderByAnnouncementDateDesc(campusList, TAG)
                        .orElse(null);

        return ResponseEntity.ofNullable(
                (getAnnouncements.isEmpty() || getAnnouncements == null) ? null :
                        getAnnouncements.stream()
                                .map((Announcement a) -> MainAnnouncementsResponse.of(a))
                                .collect(Collectors.toList()));
    }

}
