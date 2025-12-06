package com.woopaca.likeknu.job.announcement.service;

import com.woopaca.likeknu.job.announcement.AnnouncementCache;
import com.woopaca.likeknu.job.announcement.AnnouncementModifier;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("jobAnnouncementService")
public class AnnouncementService {

    private final AnnouncementCache announcementCache;
    private final AnnouncementModifier announcementModifier;

    public AnnouncementService(AnnouncementCache announcementCache, AnnouncementModifier announcementModifier) {
        this.announcementCache = announcementCache;
        this.announcementModifier = announcementModifier;
    }

    @Transactional
    public void updateAnnouncements(List<AnnouncementMessage> announcementMessages) {
        announcementMessages.stream()
                .filter(announcementCache::isAbsent)
                .forEach(announcementMessage -> {
                    try {
                        announcementModifier.appendAnnouncement(announcementMessage);
                        announcementCache.cache(announcementMessage);
                    } catch (Exception e) {
                        log.error("[AnnouncementService] 공지사항 저장 실패. announcement: {}", announcementMessage, e);
                    }
                });
    }
}
