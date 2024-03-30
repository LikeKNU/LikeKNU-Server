package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.announcement.MainAnnouncementsResponse;
import ac.knu.likeknu.controller.dto.menu.MainMenuResponse;
import ac.knu.likeknu.controller.dto.schedule.MainScheduleResponse;
import ac.knu.likeknu.domain.AcademicCalendar;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.domain.constants.MealType;
import ac.knu.likeknu.repository.AcademicCalendarRepository;
import ac.knu.likeknu.repository.AnnouncementRepository;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;
    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;
    private final AcademicCalendarRepository academicCalendarRepository;

    public List<MainAnnouncementsResponse> getAnnouncementsResponse(Campus campus) {
        List<Campus> campusList = List.of(Campus.ALL, campus);

        Sort sort = Sort.by(Order.desc("announcementDate"), Order.desc("collectedAt"));
        List<Announcement> getAnnouncements =
                announcementRepository.findTop4ByCampusInAndCategory(campusList, Category.STUDENT_NEWS, sort);

        return getAnnouncements.stream()
                .map(MainAnnouncementsResponse::of)
                .collect(Collectors.toList());
    }

    public List<MainMenuResponse> getMenuResponse(Campus campus) {
        return cafeteriaRepository.findByCampus(campus)
                .stream()
                .sorted(Comparator.comparing(Cafeteria::getSequence))
                .map(cafeteria -> findNowMealTypeMenu(cafeteria)
                        .map(menu -> MainMenuResponse.of(cafeteria, menu.getMenus()))
                        .orElse(MainMenuResponse.empty(cafeteria)))
                .toList();
    }

    private Optional<Menu> findNowMealTypeMenu(Cafeteria cafeteria) {
        return menuRepository.findByCafeteriaAndMenuDate(cafeteria, LocalDate.now())
                .stream()
                .filter(menu -> menu.getMealType().equals(MealType.now()))
                .findAny();
    }

    public List<MainScheduleResponse> getScheduleResponse() {
        List<AcademicCalendar> calendarList = academicCalendarRepository.findTop3ByStartDateBetweenOrderByStartDateAsc(
                LocalDate.now(), LocalDate.now().plusWeeks(4)
        );

        return calendarList.stream()
                .map(MainScheduleResponse::of)
                .collect(Collectors.toList());
    }
}
