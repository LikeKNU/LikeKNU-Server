package ac.knu.likeknu.controller.dto.announcement;

import ac.knu.likeknu.domain.Announcement;
import lombok.Builder;

@Builder
public record MainAnnouncementsResponse(String announcementId, String announcementTitle, String announcementUrl) {

    public static MainAnnouncementsResponse of(Announcement announcement) {
        return MainAnnouncementsResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementUrl(announcement.getAnnouncementUrl())
                .build();
    }
}
