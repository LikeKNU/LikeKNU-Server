package ac.knu.likeknu.collector.announcement.studentnews;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.announcement.dto.StudentNewsElement;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentNewsManager {

    private final AnnouncementProperties announcementProperties;

    public List<Announcement> fetchStudentNews() throws IOException {
        String url = announcementProperties.getStudentNews();
        Connection conn = Jsoup.connect(url);
        Document document = conn.get();

        int noticeSize = document.getElementsByClass("notice").size();

        Elements studentNewsTitleElements = document.select("td strong");
        Elements studentNewsLinkElements = document.select("td a");
        Elements studentNewsDateElements = document.getElementsByClass("td-date");

        List<Announcement> studentNewsList = new ArrayList<>();

        for (int i = noticeSize; i < studentNewsTitleElements.size(); i++) {
            String campus = document.selectXpath("//*[@id=\"menu12499_obj60\"]/div[2]/form[2]/div/table/tbody/tr[" + (i + 1) + "]/td[2]/a/span[1]").text();
            if (!hasCampus(campus)) campus = "";

            StudentNewsElement newsElement = StudentNewsElement.builder()
                    .studentNewsTitleElement(studentNewsTitleElements.get(i))
                    .studentNewsLinkElement(studentNewsLinkElements.get(i))
                    .studentNewsDateElement(studentNewsDateElements.get(i))
                    .campus(campus)
                    .kongjuUnivAddress(announcementProperties.getKongjuUnivAddress())
                    .build();

            studentNewsList.add(Announcement.ofStudentNews(newsElement));
        }

        return studentNewsList;
    }

    private boolean hasCampus(String campus) {
        return !(campus == null || campus.equals("새글"));
    }

}
