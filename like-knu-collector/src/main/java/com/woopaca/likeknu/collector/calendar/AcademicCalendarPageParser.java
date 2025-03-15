package com.woopaca.likeknu.collector.calendar;

import com.woopaca.likeknu.collector.calendar.dto.AcademicCalendarDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcademicCalendarPageParser {

    public List<AcademicCalendarDto> parseAcademicCalendarPage(String pageSource) {
        Document document = Jsoup.parse(pageSource);

        String year = document.selectFirst("input#year").attr("value");
        Elements elements = document.select(".sche-comt tr");
        return elements.stream()
                .map(element -> {
                    String date = element.select("th").text();
                    String contents = element.select("td a.onDate").text();
                    return AcademicCalendarDto.of(year, date, contents);
                })
                .toList();
    }
}
