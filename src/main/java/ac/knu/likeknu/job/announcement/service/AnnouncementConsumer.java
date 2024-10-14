package ac.knu.likeknu.job.announcement.service;

import ac.knu.likeknu.collector.announcement.dto.AnnouncementsMessage;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementConsumer {

    private final AnnouncementService announcementService;

    public AnnouncementConsumer(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @EventListener
    public void consumeAnnouncementMessages(@Valid AnnouncementsMessage announcements) {
        List<AnnouncementMessage> announcementMessages = announcements.announcements()
                .stream()
                .map(announcement -> AnnouncementMessage.builder()
                        .title(announcement.title())
                        .announcementUrl(announcement.announcementUrl())
                        .announcementDate(announcement.announcementDate())
                        .campus(Campus.valueOf(announcement.campus().name()))
                        .category(announcement.category())
                        .build())
                .toList();
        announcementService.updateAnnouncements(announcementMessages);
    }
}
