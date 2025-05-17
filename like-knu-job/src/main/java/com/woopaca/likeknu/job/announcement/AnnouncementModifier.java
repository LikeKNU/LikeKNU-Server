package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import com.woopaca.likeknu.repository.AnnouncementRepository;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementModifier {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTagAbstracter announcementTagAbstracter;

    public AnnouncementModifier(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
        this.announcementTagAbstracter = announcementMessage -> Tag.of(announcementMessage.getCategory().name());
    }

    public void appendAnnouncement(AnnouncementMessage announcementMessage) {
        announcementRepository.findByAnnouncementUrl(announcementMessage.getAnnouncementUrl())
                .ifPresentOrElse(
                        announcement -> announcement.update(announcementMessage.getTitle(), announcementMessage.getCampus()),
                        () -> {
                            Tag tag = announcementTagAbstracter.abstractTag(announcementMessage);
                            Announcement announcement = announcementMessage.toEntity(tag);
                            announcementRepository.save(announcement);
                        }
                );
    }
}
