package ac.knu.likeknu.collector.announcement.dto;

import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Announcement(String title, String announcementUrl, LocalDate announcementDate, Campus campus,
                           Category category) {

    public static Announcement ofDormitory(String title, String url, LocalDate date, Campus campus) {
        return ofCategory(title, url, date, campus, Category.DORMITORY);
    }

    public static Announcement ofLibrary(String title, String url, LocalDate date, Campus campus) {
        return ofCategory(title, url, date, campus, Category.LIBRARY);
    }

    public static Announcement ofStudentNews(String title, String url, LocalDate date, Campus campus) {
        return ofCategory(title, url, date, campus, Category.STUDENT_NEWS);
    }

    public static Announcement ofRecruitment(String title, String url, LocalDate date, Campus campus) {
        return ofCategory(title, url, date, campus, Category.RECRUITMENT);
    }

    private static Announcement ofCategory(String title, String url, LocalDate date, Campus campus, Category category) {
        return Announcement.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(date)
                .campus(campus)
                .category(category)
                .build();
    }
}
