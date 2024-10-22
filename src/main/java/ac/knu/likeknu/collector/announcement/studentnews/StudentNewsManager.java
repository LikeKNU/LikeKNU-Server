package ac.knu.likeknu.collector.announcement.studentnews;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.announcement.dto.StudentNewsElement;
import ac.knu.likeknu.exception.BusinessException;
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

    public List<Announcement> fetchStudentNews() {
        String url = announcementProperties.getStudentNewsUrl();
        Connection conn = Jsoup.connect(url);
        try {
            Document document = conn.get();
            int noticeSize = document.getElementsByClass("notice").size();

            Elements studentNewsTitleElements = document.select("td strong");
            Elements studentNewsLinkElements = document.select("td a");
            Elements studentNewsDateElements = document.getElementsByClass("td-date");

            List<Announcement> studentNewsList = new ArrayList<>();

            for (int i = noticeSize; i < studentNewsTitleElements.size(); i++) {
                String campus = document.selectXpath("//*[@id=\"menu12499_obj60\"]/div[2]/form[2]/div/table/tbody/tr[" + (i + 1) + "]/td[2]/a/span[1]").text();
                if (!hasCampus(campus))
                    campus = "";

                StudentNewsElement newsElement = StudentNewsElement.builder()
                        .studentNewsTitleElement(studentNewsTitleElements.get(i))
                        .studentNewsLinkElement(studentNewsLinkElements.get(i))
                        .studentNewsDateElement(studentNewsDateElements.get(i))
                        .campus(campus)
                        .kongjuUnivAddress(announcementProperties.getKongjuUniversityUrl())
                        .build();

                studentNewsList.add(Announcement.ofStudentNews(newsElement));
            }

            return studentNewsList;
        } catch (IOException e) {
            throw new BusinessException("Failed to fetch student news");
        }
    }

    private boolean hasCampus(String campus) {
        return !(campus == null || campus.equals("새글"));
    }

}
