package ac.knu.likeknu.controller.dto.response;

import lombok.Getter;

@Getter
public class MainAnnouncementsResponse {

    private String announcementId;
    private String announcementTitle;
    private String announcementUrl;

    public MainAnnouncementsResponse(String announcementId, String announcementTitle, String announcementUrl) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
    }
}
