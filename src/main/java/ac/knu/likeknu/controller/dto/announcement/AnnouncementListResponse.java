package ac.knu.likeknu.controller.dto.announcement;

import ac.knu.likeknu.domain.Announcement;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AnnouncementListResponse {

    private final String announcementId;
    private final String announcementTitle;
    private final String announcementDate;
    private final String announcementUrl;

    @Builder
    public AnnouncementListResponse(String announcementId, String announcementTitle, String announcementDate, String announcementUrl) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementDate = announcementDate;
        this.announcementUrl = announcementUrl;
    }

    public static AnnouncementListResponse of(Announcement announcement) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(dateTimeFormatter))
                .announcementUrl(announcement.getAnnouncementUrl())
                .build();
    }
}
