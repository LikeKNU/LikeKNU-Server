package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.controller.dto.announcement.AnnouncementListResponse;
import com.woopaca.likeknu.controller.dto.base.PageDto;
import com.woopaca.likeknu.controller.dto.base.PageResponseDto;
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

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
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
