package com.woopaca.likeknu.controller.dto.announcement;

import com.woopaca.likeknu.entity.AdAnnouncement;
import lombok.Builder;

@Builder
public record AdAnnouncementResponse(Long id, String title, String subTitle, String contents) {

    public static AdAnnouncementResponse from(AdAnnouncement adAnnouncement) {
        return AdAnnouncementResponse.builder()
                .id(adAnnouncement.getId())
                .title(adAnnouncement.getTitle())
                .subTitle(adAnnouncement.getSubTitle())
                .contents(adAnnouncement.getContents())
                .build();
    }
}
