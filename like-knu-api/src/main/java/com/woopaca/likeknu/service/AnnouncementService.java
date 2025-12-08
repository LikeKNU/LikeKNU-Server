package com.woopaca.likeknu.service;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.controller.dto.announcement.AnnouncementListResponse;
import com.woopaca.likeknu.controller.dto.base.PageDto;
import com.woopaca.likeknu.entity.AdAnnouncement;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.repository.AdAnnouncementRepository;
import com.woopaca.likeknu.repository.AnnouncementRepository;
import com.woopaca.likeknu.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AnnouncementService {

    private static final int DEFAULT_ANNOUNCEMENT_PAGE_SIZE = 15;

    private final AnnouncementRepository announcementRepository;
    private final DeviceRepository deviceRepository;
    private final AdAnnouncementRepository adAnnouncementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, DeviceRepository deviceRepository, AdAnnouncementRepository adAnnouncementRepository) {
        this.announcementRepository = announcementRepository;
        this.deviceRepository = deviceRepository;
        this.adAnnouncementRepository = adAnnouncementRepository;
    }

    public List<AnnouncementListResponse> getAnnouncements(Campus campus, Category category, PageDto pageDto, String deviceId) {
        int requestPage = pageDto.getCurrentPage() - 1;
        Pageable pageable = PageRequest.of(requestPage, DEFAULT_ANNOUNCEMENT_PAGE_SIZE,
                Sort.by(Order.desc("announcementDate"), Order.desc("collectedAt")));

        Slice<Announcement> announcementsPage =
                announcementRepository.findByCampusInAndCategory(Set.of(campus, Campus.ALL), category, pageable);

        return getAnnouncementResponses(deviceId, announcementsPage);
    }

    public List<AnnouncementListResponse> searchAnnouncements(Campus campus, PageDto pageDto, String keyword, String deviceId) {
        int requestPage = pageDto.getCurrentPage() - 1;
        Pageable pageable = PageRequest.of(requestPage, DEFAULT_ANNOUNCEMENT_PAGE_SIZE,
                Sort.by(Order.desc("announcementDate"), Order.desc("collectedAt")));

        Slice<Announcement> announcementsPage = announcementRepository
                .findByCampusInAndAnnouncementTitleContains(Set.of(campus, Campus.ALL), keyword, pageable);

        return getAnnouncementResponses(deviceId, announcementsPage);
    }

    public List<AnnouncementListResponse> getAdAnnouncements() {
        List<AdAnnouncement> adAnnouncements = fetchAdAnnouncementsSafely();
        return adAnnouncements.stream()
                .map(AnnouncementListResponse::ofAd)
                .toList();
    }

    private List<AdAnnouncement> fetchAdAnnouncementsSafely() {
        try {
            return adAnnouncementRepository.findByIsAdExposedIsTrue();
        } catch (Exception e) {
            log.warn("[AnnouncementService] 광고 공지사항 조회 실패.", e);
            return Collections.emptyList();
        }
    }

    private List<AnnouncementListResponse> getAnnouncementResponses(String deviceId, Slice<Announcement> announcements) {
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
