package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.response.PageDto;
import ac.knu.likeknu.controller.dto.response.PageResponseDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import ac.knu.likeknu.service.AnnouncementService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/student-news")
    public PageResponseDto<List<AnnouncementListResponse>> studentNewsList(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> studentNewsList =
                announcementService.getAnnouncements(campus, Category.SCHOOL_NEWS, pageDto);
        return PageResponseDto.of(studentNewsList, pageDto);
    }

    @GetMapping("/dormitory")
    public PageResponseDto<List<AnnouncementListResponse>> dormitoryAnnouncementList(
            @RequestParam("campus") Campus campus,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        PageDto pageDto = PageDto.of(page);
        List<AnnouncementListResponse> dormitoryAnnouncementList =
                announcementService.getAnnouncements(campus, Category.DORMITORY, pageDto);
        return PageResponseDto.of(dormitoryAnnouncementList, pageDto);
    }
}
