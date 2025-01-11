package ac.knu.likeknu.collector.announcement.dto;

import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Announcement(String title, String announcementUrl, LocalDate announcementDate, Campus campus,
                           Category category) {

    public static Announcement ofDormitory(String title, String url, LocalDate date, Campus campus) {
        return Announcement.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(date)
                .campus(campus)
                .category(Category.DORMITORY)
                .build();
    }

    public static Announcement ofLibrary(String title, String url, LocalDate date, Campus campus) {
        return Announcement.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(date)
                .campus(campus)
                .category(Category.LIBRARY)
                .build();
    }

    public static Announcement ofStudentNews(String title, String url, LocalDate date, Campus campus) {
        return Announcement.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(date)
                .campus(campus)
                .category(Category.STUDENT_NEWS)
                .build();
    }

    public static Announcement ofRecruitment(String title, String url, LocalDate date, Campus campus) {
        return Announcement.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(date)
                .campus(campus)
                .category(Category.RECRUITMENT)
                .build();
    }
}
