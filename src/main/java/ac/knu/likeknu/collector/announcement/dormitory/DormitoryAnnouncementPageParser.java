package ac.knu.likeknu.collector.announcement.dormitory;

import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.calendar.WebProperties;
import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DormitoryAnnouncementPageParser {

    private final WebProperties webProperties;

    public DormitoryAnnouncementPageParser(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    public List<Announcement> parseDormitoryAnnouncementPage(String pageSource, Campus campus) {
        Document document = Jsoup.parse(pageSource);
        Element tbodyElement = document.select("table.table-board tbody").first();
        List<Announcement> dormitoryAnnouncements = new ArrayList<>();
        if (tbodyElement != null) {
            Elements rows = tbodyElement.select("tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 6) {
                    String title = columns.get(2).text();
                    String date = columns.get(3).text();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate announcementDate = LocalDate.parse(date, dateTimeFormatter);

                    Element linkElement = columns.get(2).select("a").first();
                    String url = webProperties.getDormitoryAnnouncement() + linkElement.attr("href");

                    dormitoryAnnouncements.add(new Announcement(title, url, announcementDate, campus, Category.DORMITORY));
                }
            }
        }

        return dormitoryAnnouncements;
    }
}
