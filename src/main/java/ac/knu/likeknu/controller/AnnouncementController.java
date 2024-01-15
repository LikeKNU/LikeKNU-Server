package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.base.PageDto;
import ac.knu.likeknu.controller.dto.base.PageResponseDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import ac.knu.likeknu.service.AnnouncementService;
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
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @PathVariable String category
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> studentNewsList =
                announcementService.getAnnouncements(campus, Category.of(category), pageDto, keyword.trim());
        return PageResponseDto.of(studentNewsList, pageDto);
    }
}
