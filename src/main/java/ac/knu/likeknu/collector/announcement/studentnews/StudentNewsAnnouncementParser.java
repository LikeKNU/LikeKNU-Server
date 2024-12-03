package ac.knu.likeknu.collector.announcement.studentnews;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.menu.constants.Campus;
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
                .map(row -> {
                    String dateText = row.select("td.td-date").text();
                    String baseHref = row.select("a").attr("href");
                    String campusText = row.select("span.cate").text();

                    String title = row.select("strong").text();
                    String url = studentNewsURLExtractor.extractRedirectURL(
                            announcementProperties.getKongjuUniversityUrl() + baseHref
                    );
                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                    Campus campus = campusText.isEmpty() ? Campus.ALL : Arrays.stream(Campus.values())
                            .filter(it -> campusText.contains(it.getCampusLocation()))
                            .findAny()
                            .orElse(Campus.ALL);
                    return Announcement.ofStudentNews(title, url, date, campus);
                })
                .toList();
    }
}
