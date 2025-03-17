package com.woopaca.likeknu.service;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.controller.dto.announcement.AnnouncementListResponse;
import com.woopaca.likeknu.controller.dto.base.PageDto;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.repository.AnnouncementRepository;
import com.woopaca.likeknu.repository.DeviceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnnouncementService {

    private static final int DEFAULT_ANNOUNCEMENT_PAGE_SIZE = 15;

    private final AnnouncementRepository announcementRepository;
    private final DeviceRepository deviceRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, DeviceRepository deviceRepository) {
        this.announcementRepository = announcementRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<AnnouncementListResponse> getAnnouncements(Campus campus, Category category, PageDto pageDto, String deviceId) {
        int requestPage = pageDto.getCurrentPage() - 1;
        Pageable pageable = PageRequest.of(requestPage, DEFAULT_ANNOUNCEMENT_PAGE_SIZE,
                Sort.by(Order.desc("announcementDate"), Order.desc("collectedAt")));

        Slice<Announcement> announcementsPage =
                announcementRepository.findByCampusInAndCategory(Set.of(campus, Campus.ALL), category, pageable);

        return getAnnouncementListResponses(deviceId, announcementsPage);
    }

    public List<AnnouncementListResponse> searchAnnouncements(Campus campus, PageDto pageDto, String keyword, String deviceId) {
        int requestPage = pageDto.getCurrentPage() - 1;
        Pageable pageable = PageRequest.of(requestPage, DEFAULT_ANNOUNCEMENT_PAGE_SIZE,
                Sort.by(Order.desc("announcementDate"), Order.desc("collectedAt")));

        Slice<Announcement> announcementsPage = announcementRepository
                .findByCampusInAndAnnouncementTitleContains(Set.of(campus, Campus.ALL), keyword, pageable);

        return getAnnouncementListResponses(deviceId, announcementsPage);
    }

    private List<AnnouncementListResponse> getAnnouncementListResponses(String deviceId, Slice<Announcement> announcements) {
        if (deviceId != null) {
            Optional<Device> findDevice = deviceRepository.findById(deviceId);
            if (findDevice.isPresent()) {
                Device device = findDevice.get();
                return announcements.stream()
                        .map(announcement -> AnnouncementListResponse.of(announcement, device.getBookmarks()))
                        .toList();
            }
        }
        return announcements.stream()
                .map(AnnouncementListResponse::of)
                .toList();
    }
}
