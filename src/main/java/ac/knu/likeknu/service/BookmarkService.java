package ac.knu.likeknu.service;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.AnnouncementRepository;
import ac.knu.likeknu.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service
public class BookmarkService {

    private final DeviceRepository deviceRepository;
    private final AnnouncementRepository announcementRepository;

    public BookmarkService(DeviceRepository deviceRepository, AnnouncementRepository announcementRepository) {
        this.deviceRepository = deviceRepository;
        this.announcementRepository = announcementRepository;
    }

    public void addAnnouncementBookmark(String deviceId, String announcementId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BusinessException(String.format("Announcement not found! [%s]", announcementId)));

        Set<Announcement> bookmarks = device.getBookmarks();
        if (bookmarks.contains(announcement)) {
            return;
        }
        bookmarks.add(announcement);
    }
}
