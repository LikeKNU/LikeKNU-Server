package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.AnnouncementRepository;
import ac.knu.likeknu.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class BookmarkService {

    private static final int DEFAULT_PAGE_SIZE = 15;

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

    public void removeAnnouncementBookmark(String deviceId, String announcementId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BusinessException(String.format("Announcement not found! [%s]", announcementId)));

        Set<Announcement> bookmarks = device.getBookmarks();
        bookmarks.remove(announcement);
    }

    public List<AnnouncementListResponse> getBookmarkAnnouncements(String deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));

        return device.getBookmarks()
                .stream()
                .sorted(Comparator.comparing(Announcement::getAnnouncementDate).reversed())
                .map(AnnouncementListResponse::bookmarks)
                .toList();
    }
}
