package ac.knu.likeknu.collector.announcement.dormitory;

import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.calendar.WebProperties;
import ac.knu.likeknu.collector.menu.constants.Campus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class DormitoryAnnouncementPageParser {

    private final WebProperties webProperties;

    public DormitoryAnnouncementPageParser(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    public List<Announcement> parseDormitoryAnnouncementPage(String pageSource, Campus campus) {
        Document document = Jsoup.parse(pageSource);
        Element tableBodyElement = document.select("table.table-board tbody").first();
        if (tableBodyElement == null) {
            return Collections.emptyList();
        }

        Elements announcementRows = tableBodyElement.select("tr");
        return announcementRows.stream()
                .map(announcementRow -> announcementRow.select("td"))
                .filter(announcementColumns -> announcementColumns.size() >= 6)
                .map(DormitoryAnnouncementElements::new)
                .map(announcementElements -> {
                    String title = announcementElements.getTitle();
                    LocalDate date = announcementElements.getDate();
                    String url = webProperties.getDormitoryAnnouncement() + announcementElements.getPath();
                    return Announcement.ofDormitory(title, url, date, campus);
                })
                .toList();
    }
}
