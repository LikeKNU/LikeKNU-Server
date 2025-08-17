package com.woopaca.likeknu.job.calendar;

import com.woopaca.likeknu.job.calendar.dto.AcademicCalendarMessage;
import com.woopaca.likeknu.repository.AcademicCalendarRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CalendarCache {

    private final Map<Year, Set<AcademicCalendarMessage>> memory = new ConcurrentHashMap<>();

    private final AcademicCalendarRepository academicCalendarRepository;

    public CalendarCache(AcademicCalendarRepository academicCalendarRepository) {
        this.academicCalendarRepository = academicCalendarRepository;
    }

    @PostConstruct
    private void loadCache() {
        Year currentYear = Year.now();
        memory.put(currentYear, Collections.synchronizedSet(new HashSet<>()));
        memory.put(currentYear.plusYears(1), Collections.synchronizedSet(new HashSet<>()));

        LocalDate startDate = LocalDate.now()
                .minusMonths(1)
                .withDayOfMonth(1);
        academicCalendarRepository.findByStartDateGreaterThanEqual(startDate)
                .stream()
                .map(AcademicCalendarMessage::from)
                .forEach(this::cache);
    }

    public AcademicCalendarMessage cache(AcademicCalendarMessage academicCalendarMessage) {
        LocalDate startDate = academicCalendarMessage.getStartDate();
        Year year = Year.of(startDate.getYear());
        Set<AcademicCalendarMessage> academicCalendarMessages = memory.get(year);
        academicCalendarMessages.add(academicCalendarMessage);
        return academicCalendarMessage;
    }

    public boolean isAbsent(AcademicCalendarMessage academicCalendarMessage) {
        LocalDate startDate = academicCalendarMessage.getStartDate();
        Year year = Year.of(startDate.getYear());
        Set<AcademicCalendarMessage> academicCalendarMessages = memory.get(year);
        return !academicCalendarMessages.contains(academicCalendarMessage);
    }

    @Scheduled(cron = "0 1 0 1 1 *")
    public void scheduledCalendarCache() {
        Year currentYear = Year.now();
        memory.remove(currentYear.minusYears(1));
        memory.put(currentYear.plusYears(1), Collections.synchronizedSet(new HashSet<>()));
    }
}
