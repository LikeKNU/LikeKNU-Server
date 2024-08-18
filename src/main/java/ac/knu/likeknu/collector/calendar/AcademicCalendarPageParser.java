package ac.knu.likeknu.collector.calendar;

import ac.knu.likeknu.collector.calendar.dto.AcademicCalendar;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AcademicCalendarPageParser {

    public List<AcademicCalendar> parseAcademicCalendarPage(String pageSource) {
        Document document = Jsoup.parse(pageSource);

        String year = document.selectFirst("input#year").attr("value");
        Elements elements = document.select(".sche-comt tr");
        return elements.stream()
                .map(element -> {
                    String date = element.select("th").text();
                    String contents = element.select("td a.onDate").text();
                    return AcademicCalendar.of(year, date, contents);
                })
                .toList();
    }
}
