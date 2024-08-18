package ac.knu.likeknu.collector.announcement.dto;

import ac.knu.likeknu.collector.announcement.studentnews.StudentNewsURLExtractor;
import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record Announcement(String title, String announcementUrl, LocalDate announcementDate, Campus campus,
                           Category category) {

    public static Announcement ofStudentNews(StudentNewsElement studentNewsElement) {
        String date = studentNewsElement.studentNewsDateElement().text();
        String link = studentNewsElement.kongjuUnivAddress() + studentNewsElement.studentNewsLinkElement().attr("href");
        String campus = studentNewsElement.campus();

        Campus campusType = Campus.ALL;

        for (Campus c : Campus.values()) {
            if (campus.contains(c.getCampusLocation()))
                campusType = c;
        }

        return Announcement.builder()
                .title(studentNewsElement.studentNewsTitleElement().text())
                .announcementUrl(StudentNewsURLExtractor.extractRedirectURL(link))
                .announcementDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .campus(campusType)
                .category(Category.STUDENT_NEWS)
                .build();
    }
}
