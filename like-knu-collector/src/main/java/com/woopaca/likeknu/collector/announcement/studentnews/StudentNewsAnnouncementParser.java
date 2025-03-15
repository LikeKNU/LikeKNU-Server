package com.woopaca.likeknu.collector.announcement.studentnews;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import com.woopaca.likeknu.collector.announcement.dto.Announcement;
import com.woopaca.likeknu.collector.menu.constants.Campus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentNewsAnnouncementParser {

    private final AnnouncementProperties announcementProperties;
    private final StudentNewsURLExtractor studentNewsURLExtractor;

    public List<Announcement> parseStudentNewsAnnouncementPage(String pageSource) {
        Document document = Jsoup.parse(pageSource);

        Elements rows = document.select("table.board-table tbody tr");
        return rows.stream()
                .filter(element -> !element.classNames().contains("notice"))
                .map(row -> {
                    String dateText = row.select("td.td-date").text();
                    String baseHref = row.select("a").attr("href");
                    String campusText = row.select("span.cate").text();

                    String title = row.select("strong").text();
                    String url = studentNewsURLExtractor.extractRedirectURL(
                            announcementProperties.getKongjuUniversityUrl() + baseHref
                    );
                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                    Campus campus = Arrays.stream(Campus.values())
                            .filter(it -> campusText.contains(it.getCampusLocation()))
                            .findAny()
                            .orElse(Campus.ALL);
                    return Announcement.ofStudentNews(title, url, date, campus);
                })
                .toList();
    }
}
