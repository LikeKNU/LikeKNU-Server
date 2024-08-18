package ac.knu.likeknu.job.announcement.dto;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class AnnouncementMessage {

    @NotBlank
    private String title;
    @NotBlank
    private String announcementUrl;
    @NotNull
    private LocalDate announcementDate;
    @NotNull
    private Campus campus;
    @NotNull
    private Category category;

    protected AnnouncementMessage() {
    }

    @Builder
    public AnnouncementMessage(String title, String announcementUrl, LocalDate announcementDate, Campus campus, Category category) {
        this.title = title;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.category = category;
    }

    public static AnnouncementMessage of(Announcement announcement) {
        return AnnouncementMessage.builder()
                .title(announcement.getAnnouncementTitle())
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementDate(announcement.getAnnouncementDate())
                .campus( announcement.getCampus())
                .category(announcement.getCategory())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        AnnouncementMessage that = (AnnouncementMessage) object;

        if (!Objects.equals(title, that.title))
            return false;
        if (!Objects.equals(announcementUrl, that.announcementUrl))
            return false;
        if (!Objects.equals(announcementDate, that.announcementDate))
            return false;
        if (campus != that.campus)
            return false;
        return category == that.category;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (announcementUrl != null ? announcementUrl.hashCode() : 0);
        result = 31 * result + (announcementDate != null ? announcementDate.hashCode() : 0);
        result = 31 * result + (campus != null ? campus.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
