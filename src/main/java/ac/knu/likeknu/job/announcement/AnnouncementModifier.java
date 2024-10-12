package ac.knu.likeknu.job.announcement;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.constants.Tag;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
import ac.knu.likeknu.repository.AnnouncementRepository;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementModifier {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTagAbstracter announcementTagAbstracter;

    public AnnouncementModifier(AnnouncementRepository announcementRepository, AnnouncementTagAbstracter announcementTagAbstracter) {
        this.announcementRepository = announcementRepository;
        this.announcementTagAbstracter = announcementTagAbstracter;
    }

    public void appendAnnouncement(AnnouncementMessage announcementMessage) {
        announcementRepository.findByAnnouncementUrl(announcementMessage.getAnnouncementUrl())
                .ifPresentOrElse(
                        announcement -> announcement.update(announcementMessage),
                        () -> {
                            Tag tag = announcementTagAbstracter.abstractTag(announcementMessage);
                            Announcement announcement = Announcement.of(announcementMessage, tag);
                            announcementRepository.save(announcement);
                        }
                );
    }
}
