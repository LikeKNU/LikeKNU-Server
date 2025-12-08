package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.controller.dto.announcement.AdAnnouncementResponse;
import com.woopaca.likeknu.controller.dto.announcement.AnnouncementListResponse;
import com.woopaca.likeknu.controller.dto.base.PageDto;
import com.woopaca.likeknu.controller.dto.base.PageResponseDto;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.repository.AdAnnouncementRepository;
import com.woopaca.likeknu.service.AnnouncementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/announcements")
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AdAnnouncementRepository adAnnouncementRepository;

    public AnnouncementController(AnnouncementService announcementService, AdAnnouncementRepository adAnnouncementRepository) {
        this.announcementService = announcementService;
        this.adAnnouncementRepository = adAnnouncementRepository;
    }

    @GetMapping("/{category}")
    public PageResponseDto<List<AnnouncementListResponse>> recentAnnouncementList(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @PathVariable("category") String category,
            @RequestParam(name = "deviceId", required = false) String deviceId
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> studentNewsList =
                announcementService.getAnnouncements(campus, Category.of(category), pageDto, deviceId);
        return PageResponseDto.of(studentNewsList, pageDto);
    }

    @GetMapping("/ad")
    public ResponseDto<List<AnnouncementListResponse>> adAnnouncementList() {
        List<AnnouncementListResponse> adAnnouncements = announcementService.getAdAnnouncements();
        return ResponseDto.of(adAnnouncements);
    }

    @GetMapping("/ad/{id}")
    public ResponseDto<AdAnnouncementResponse> adAnnouncement(@PathVariable("id") Long id) {
        AdAnnouncementResponse adAnnouncement = adAnnouncementRepository.findById(id)
                .map(AdAnnouncementResponse::from)
                .orElseThrow();
        return ResponseDto.of(adAnnouncement);
    }

    @GetMapping
    public PageResponseDto<List<AnnouncementListResponse>> searchAnnouncement(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "deviceId", required = false) String deviceId
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> announcements =
                announcementService.searchAnnouncements(campus, pageDto, keyword.trim(), deviceId);
        return PageResponseDto.of(announcements, pageDto);
    }
}
