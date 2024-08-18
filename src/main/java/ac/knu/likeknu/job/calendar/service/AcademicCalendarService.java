package ac.knu.likeknu.job.calendar.service;

import ac.knu.likeknu.domain.AcademicCalendar;
import ac.knu.likeknu.job.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknu.repository.AcademicCalendarRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service("jobAcademicCalendarService")
@RequiredArgsConstructor
public class AcademicCalendarService {

    private static final Map<Year, Set<AcademicCalendarMessage>> CALENDAR_CACHE = new ConcurrentHashMap<>();

    private final AcademicCalendarRepository academicCalendarRepository;

    @PostConstruct
    void init() {
        Year nowYear = Year.now();
        CALENDAR_CACHE.put(nowYear, Collections.synchronizedSet(new HashSet<>()));
        CALENDAR_CACHE.put(nowYear.plusYears(1), Collections.synchronizedSet(new HashSet<>()));

        importFromCalendarRepositoryAndCache();
    }

    private void importFromCalendarRepositoryAndCache() {
        academicCalendarRepository.findByStartDateGreaterThanEqual(LocalDate.now())
                .stream()
                .map(AcademicCalendarMessage::of)
                .forEach(this::caching);
    }

    private static Set<AcademicCalendarMessage> getAcademicClendarCacheSet(AcademicCalendarMessage academicCalendarMessage) {
        LocalDate startDate = academicCalendarMessage.getStartDate();
        Year year = Year.of(startDate.getYear());
        return CALENDAR_CACHE.get(year);
    }

    public void updateCalendar(AcademicCalendarMessage academicCalendarMessage) {
        if (isAlreadyCollected(academicCalendarMessage)) {
            return;
        }

        caching(academicCalendarMessage);

        try {
            academicCalendarRepository.save(AcademicCalendar.of(academicCalendarMessage));
        } catch (DataIntegrityViolationException ignore) {
        }
    }

    private boolean isAlreadyCollected(AcademicCalendarMessage academicCalendarMessage) {
        Set<AcademicCalendarMessage> academicCalendarMessages = getAcademicClendarCacheSet(academicCalendarMessage);
        return academicCalendarMessages.contains(academicCalendarMessage);
    }

    private void caching(AcademicCalendarMessage academicCalendarMessage) {
        Set<AcademicCalendarMessage> academicCalendarMessages = getAcademicClendarCacheSet(academicCalendarMessage);
        academicCalendarMessages.add(academicCalendarMessage);
    }

    /**
     * 매년 1월 1일 00시 캐시 초기화
     */
    @Scheduled(cron = "0 0 0 1 1 *")
    public void scheduledCalendarCache() {
        Year nowYear = Year.now();
        CALENDAR_CACHE.remove(nowYear.minusYears(1));
        CALENDAR_CACHE.put(nowYear.plusYears(1), Collections.synchronizedSet(new HashSet<>()));
    }
}
