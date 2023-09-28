package ac.knu.likeknu.controller.dto.announcement;

import lombok.Builder;
import lombok.Getter;

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
}
