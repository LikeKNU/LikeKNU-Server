package com.woopaca.likeknu.controller.dto.announcement;

import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.entity.AdAnnouncement;
import com.woopaca.likeknu.entity.Announcement;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Builder
public record AnnouncementListResponse(String announcementId, String announcementTitle, String announcementDate,
                                       String announcementUrl, String announcementTag, boolean isBookmarked,
                                       boolean isAd, String subTitle) {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static AnnouncementListResponse of(Announcement announcement) {
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(DATE_TIME_FORMATTER))
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementTag(announcement.getTag().getTagName())
                .build();
    }

    public static AnnouncementListResponse of(Announcement announcement, Set<Announcement> bookmarks) {
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(DATE_TIME_FORMATTER))
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementTag(announcement.getTag().getTagName())
                .isBookmarked(bookmarks.contains(announcement))
                .build();
    }

    public static AnnouncementListResponse bookmarks(Announcement announcement) {
        return AnnouncementListResponse.builder()
                .announcementId(announcement.getId())
                .announcementTitle(announcement.getAnnouncementTitle())
                .announcementDate(announcement.getAnnouncementDate().format(DATE_TIME_FORMATTER))
                .announcementUrl(announcement.getAnnouncementUrl())
                .announcementTag(announcement.getTag().getTagName())
                .isBookmarked(true)
                .build();
    }

    public static AnnouncementListResponse ofAd(AdAnnouncement adAnnouncement) {
        return AnnouncementListResponse.builder()
                .announcementId(String.valueOf(adAnnouncement.getId()))
                .announcementTitle(adAnnouncement.getTitle())
                .announcementDate(LocalDateTime.now().format(DATE_TIME_FORMATTER))
                .announcementUrl(null)
                .announcementTag(Tag.ETC.name())
                .isAd(true)
                .subTitle(adAnnouncement.getSubTitle())
                .build();
    }
}
