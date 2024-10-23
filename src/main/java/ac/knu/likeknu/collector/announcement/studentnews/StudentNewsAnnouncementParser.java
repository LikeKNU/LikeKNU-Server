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
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class StudentNewsAnnouncementParser {

    private final AnnouncementProperties announcementProperties;
    private final StudentNewsURLExtractor studentNewsURLExtractor;

    public List<Announcement> parseStudentNewsAnnouncementPage(String pageSource) {
        Document document = Jsoup.parse(pageSource);

        int noticesSize = document.getElementsByClass("notice")
                .size();
        Elements studentNewsTitleElements = document.select("td strong");
        Elements studentNewsUrlElements = document.select("td a");
        Elements studentNewsDateElements = document.getElementsByClass("td-date");

        return IntStream.range(noticesSize, studentNewsTitleElements.size())
                .mapToObj(i -> {
                    String title = studentNewsTitleElements.get(i).text();
                    String oldUrl = announcementProperties.getKongjuUniversityUrl() + studentNewsUrlElements.get(i)
                            .attr("href");
                    String url = studentNewsURLExtractor.extractRedirectURL(oldUrl);
                    String dateText = studentNewsDateElements.get(i).text();
                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                    String campusText = document.select("table tbody tr:nth-child(" + (i + 1) + ") td:nth-child(2) a span:nth-child(1)").text();

                    String campus = hasCampus(campusText) ? campusText : "";
                    Campus campusType = Arrays.stream(Campus.values())
                            .filter(campusValue -> campus.contains(campusValue.getCampusLocation()))
                            .findAny()
                            .orElse(Campus.ALL);

                    return Announcement.ofStudentNews(title, url, date, campusType);
                })
                .toList();
    }

    private boolean hasCampus(String campus) {
        return !(campus == null || campus.equals("새글"));
    }

}
