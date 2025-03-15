package com.woopaca.likeknu.job.announcement.service;

import com.woopaca.likeknu.job.announcement.AnnouncementCache;
import com.woopaca.likeknu.job.announcement.AnnouncementModifier;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .map(announcementCache::cache)
                .forEach(announcementModifier::appendAnnouncement);
    }
}
