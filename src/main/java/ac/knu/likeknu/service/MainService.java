package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.controller.dto.response.MainMenuResponse;
import ac.knu.likeknu.controller.dto.response.ResponseDto;
import ac.knu.likeknu.domain.*;
import ac.knu.likeknu.repository.AnnouncementRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.lettuce.core.GeoArgs.Unit.m;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;
    private final MenuRepository menuRepository;

    public ResponseEntity<List<MainAnnouncementsResponse>> getAnnouncementsResponse(Campus campus) {
        List<Campus> campusList = List.of(Campus.ALL, campus);

        Optional<List<Announcement>> getAnnouncementsOptinal =
                announcementRepository
                        .findTop4ByCampusInAndCategoryOrderByAnnouncementDateDesc(campusList, Category.SCHOOL_NEWS);

        if(getAnnouncementsOptinal.isEmpty())
            log.info("게시물이 없습니다.");

        return ResponseEntity.ok(
                getAnnouncementsOptinal.get().stream()
                        .map((Announcement a) -> MainAnnouncementsResponse.of(a))
                        .collect(Collectors.toList())
        );
    }

    public List<MainMenuResponse> getMenuResponse(Campus campus) {
        int hour = LocalDateTime.now().getHour();

        Optional<List<Menu>> getTodayMenu = menuRepository.findByDateAndCampusAndMealType(LocalDate.now(), campus, MealType.of(hour));

        return getTodayMenu.get().stream()
                .map((Menu m) -> MainMenuResponse.of(m))
                .collect(Collectors.toList());
    }

}
