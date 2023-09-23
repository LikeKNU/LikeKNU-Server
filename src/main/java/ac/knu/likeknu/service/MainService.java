package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import ac.knu.likeknu.controller.dto.response.MainMenuResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.MealType;
import ac.knu.likeknu.repository.AnnouncementRepository;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;
    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    public List<MainAnnouncementsResponse> getAnnouncementsResponse(Campus campus) {
        List<Campus> campusList = List.of(Campus.ALL, campus);

        List<Announcement> getAnnouncements =
                announcementRepository
                        .findTop4ByCampusInAndCategoryOrderByAnnouncementDateDesc(campusList, Category.SCHOOL_NEWS);

        return getAnnouncements.stream()
                        .map((Announcement a) -> MainAnnouncementsResponse.of(a))
                        .collect(Collectors.toList());
    }

    public List<MainMenuResponse> getMenuResponse(Campus campus) {
        MealType mealType = MealType.of();

        List<Menu> getTodayMenu = menuRepository.findByDateAndCampusAndMealType(LocalDate.now(), campus, mealType);
        Cafeteria cafeteria = cafeteriaRepository.findByCampus(campus)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

        String time = cafeteria.getTime();

        return getTodayMenu.stream()
                .map((Menu m) -> MainMenuResponse.of(m))
                .collect(Collectors.toList());
    }

}
