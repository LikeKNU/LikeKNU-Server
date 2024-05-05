package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.base.PageDto;
import ac.knu.likeknu.controller.dto.base.PageResponseDto;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.logging.domain.value.LogType;
import ac.knu.likeknu.logging.service.LoggingService;
import ac.knu.likeknu.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/announcements")
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final LoggingService loggingService;

    public AnnouncementController(AnnouncementService announcementService, LoggingService loggingService) {
        this.announcementService = announcementService;
        this.loggingService = loggingService;
    }

    @GetMapping("/{category}")
    public PageResponseDto<List<AnnouncementListResponse>> recentAnnouncementList(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @PathVariable("category") String category,
            @RequestHeader(name = "Device-Id", required = false) String deviceId,
            @RequestParam(name = "deviceId", required = false) String nativeDeviceId
    ) {
        if (StringUtils.hasText(keyword)) {
            loggingService.addLog(LogType.SEARCH_ANNOUNCEMENT, deviceId, category, keyword);
        }

        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> studentNewsList =
                announcementService.getAnnouncements(campus, Category.of(category), pageDto, keyword.trim(), nativeDeviceId);
        return PageResponseDto.of(studentNewsList, pageDto);
    }

    @GetMapping
    public PageResponseDto<List<AnnouncementListResponse>> searchAnnouncement(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> announcements =
                announcementService.searchAnnouncements(campus, pageDto, keyword.trim());
        return PageResponseDto.of(announcements, pageDto);
    }
}
