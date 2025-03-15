package com.woopaca.likeknu.collector.calendar.scheduler;

import com.woopaca.likeknu.collector.calendar.AcademicCalendarPageParser;
import com.woopaca.likeknu.collector.calendar.AcademicCalendarRequestManager;
import com.woopaca.likeknu.collector.calendar.dto.AcademicCalendarDto;
import com.woopaca.likeknu.collector.calendar.dto.CalendarsMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class AcademicCalendarScheduleService {

    private final AcademicCalendarRequestManager academicCalendarRequestManager;
    private final AcademicCalendarPageParser academicCalendarPageParser;
    private final AcademicCalendarProducer academicCalendarProducer;

    public AcademicCalendarScheduleService(AcademicCalendarRequestManager academicCalendarRequestManager, AcademicCalendarPageParser academicCalendarPageParser, AcademicCalendarProducer academicCalendarProducer) {
        this.academicCalendarRequestManager = academicCalendarRequestManager;
        this.academicCalendarPageParser = academicCalendarPageParser;
        this.academicCalendarProducer = academicCalendarProducer;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void schedulingAcademicCalendarProduce() {
        LocalDate currentDate = LocalDate.now();
        List<AcademicCalendarDto> academicCalendarList = IntStream.range(0, 12)
                .mapToObj(currentDate::plusMonths)
                .map(date -> {
                    String pageSource = academicCalendarRequestManager
                            .fetchAcademicCalendarPage(date.getYear(), date.getMonthValue());
                    return academicCalendarPageParser.parseAcademicCalendarPage(pageSource);
                })
                .flatMap(List::stream)
                .toList();

        academicCalendarProducer.produce(new CalendarsMessage(academicCalendarList));
    }
}
