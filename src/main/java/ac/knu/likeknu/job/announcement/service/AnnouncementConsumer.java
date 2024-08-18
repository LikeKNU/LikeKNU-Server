package ac.knu.likeknu.job.announcement.service;

import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementConsumer {

    private final AnnouncementService announcementService;

    public AnnouncementConsumer(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @EventListener
    public void consumeAnnouncementMessage(@Valid Announcement announcement) {
        AnnouncementMessage announcementMessage = AnnouncementMessage.builder()
                .title(announcement.title())
                .announcementUrl(announcement.announcementUrl())
                .announcementDate(announcement.announcementDate())
                .campus(Campus.valueOf(announcement.campus().name()))
                .category(announcement.category())
                .build();
        announcementService.updateAnnouncement(announcementMessage);
    }
}
