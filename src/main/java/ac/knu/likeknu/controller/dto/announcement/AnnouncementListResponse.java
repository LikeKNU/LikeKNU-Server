package ac.knu.likeknu.controller.dto.announcement;

import ac.knu.likeknu.domain.Announcement;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.Set;

@Builder
public record AnnouncementListResponse(String announcementId, String announcementTitle, String announcementDate,
                                       String announcementUrl, String announcementTag, boolean isBookmarked) {

    public static AnnouncementListResponse of(Announcement announcement) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(dateTimeFormatter))
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementTag(announcement.getTag().getTagName())
                .build();
    }

    public static AnnouncementListResponse of(Announcement announcement, Set<Announcement> bookmarks) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(dateTimeFormatter))
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementTag(announcement.getTag().getTagName())
                .isBookmarked(bookmarks.contains(announcement))
                .build();
    }
}
