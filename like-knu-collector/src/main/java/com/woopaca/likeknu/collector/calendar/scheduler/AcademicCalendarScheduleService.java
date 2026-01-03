package com.woopaca.likeknu.collector.calendar.scheduler;

import com.woopaca.likeknu.collector.calendar.AcademicCalendarPageParser;
import com.woopaca.likeknu.collector.calendar.AcademicCalendarRequestManager;
import com.woopaca.likeknu.collector.calendar.dto.AcademicCalendarDto;
import com.woopaca.likeknu.collector.calendar.dto.CalendarsMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class AcademicCalendarScheduleService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AcademicCalendarRequestManager academicCalendarRequestManager;
    private final AcademicCalendarPageParser academicCalendarPageParser;

    public AcademicCalendarScheduleService(ApplicationEventPublisher applicationEventPublisher, AcademicCalendarRequestManager academicCalendarRequestManager, AcademicCalendarPageParser academicCalendarPageParser) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.academicCalendarRequestManager = academicCalendarRequestManager;
        this.academicCalendarPageParser = academicCalendarPageParser;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void schedulingAcademicCalendarProduce() {
        LocalDate currentDate = LocalDate.now();
        List<AcademicCalendarDto> academicCalendarList = IntStream.range(0, 12)
                .mapToObj(currentDate::plusMonths)
                .map(date ->
                        academicCalendarRequestManager.fetchAcademicCalendarPage(date.getYear(), date.getMonthValue()))
                .map(academicCalendarPageParser::parseAcademicCalendarPage)
                .flatMap(List::stream)
                .toList();

        applicationEventPublisher.publishEvent(new CalendarsMessage(academicCalendarList));
    }
}
