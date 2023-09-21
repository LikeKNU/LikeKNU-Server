package ac.knu.likeknu.controller.dto.response;

import ac.knu.likeknu.domain.Announcement;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainAnnouncementsResponse {

    private String announcementId;
    private String announcementTitle;
    private String announcementUrl;

    @Builder
    public MainAnnouncementsResponse(String announcementId, String announcementTitle, String announcementUrl) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
    }

    public static MainAnnouncementsResponse of(Announcement announcement) {
        return MainAnnouncementsResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementUrl(announcement.getAnnouncementUrl())
                .build();
    }
}
