package com.woopaca.likeknu.controller.dto.announcement;

import com.woopaca.likeknu.entity.Announcement;
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
