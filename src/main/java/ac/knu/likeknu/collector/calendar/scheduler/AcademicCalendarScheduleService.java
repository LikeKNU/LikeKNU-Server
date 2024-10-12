package ac.knu.likeknu.collector.calendar.scheduler;

import ac.knu.likeknu.collector.calendar.AcademicCalendarPageParser;
import ac.knu.likeknu.collector.calendar.AcademicCalendarRequestManager;
import ac.knu.likeknu.collector.calendar.dto.AcademicCalendar;
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
        List<AcademicCalendar> academicCalendarList = IntStream.range(0, 12)
                .mapToObj(currentDate::plusMonths)
                .map(date -> {
                    String pageSource = academicCalendarRequestManager
                            .fetchAcademicCalendarPage(date.getYear(), date.getMonthValue());
                    return academicCalendarPageParser.parseAcademicCalendarPage(pageSource);
                })
                .flatMap(List::stream)
                .toList();

        academicCalendarList.forEach(academicCalendarProducer::produce);
    }
}
