package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.collector.announcement.scheduler.AnnouncementCollectScheduleService;
import com.woopaca.likeknu.collector.menu.scheduler.MenuSchedulingService;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RemoteCollectController {

    private final MenuSchedulingService menuSchedulingService;
    private final AnnouncementCollectScheduleService announcementCollectScheduleService;

    @Value("${admin.token}")
    private String adminToken;

    public RemoteCollectController(MenuSchedulingService menuSchedulingService, AnnouncementCollectScheduleService announcementCollectScheduleService) {
        this.menuSchedulingService = menuSchedulingService;
        this.announcementCollectScheduleService = announcementCollectScheduleService;
    }

    @PostMapping("/collect/v1/menus")
    public ResponseDto<String> collectMenu(@RequestHeader("Authorization") String authorization) {
        if (!adminToken.equals(authorization)) {
            return ResponseDto.of("권한이 없습니다.");
        }

        menuSchedulingService.scheduleMenuProduce();
        return ResponseDto.of("메뉴 수집 완료");
    }

    @PostMapping("/collect/v1/announcements/{category}")
    public ResponseDto<String> collectAnnouncement(@RequestHeader("Authorization") String authorization,
                                                   @PathVariable("category") String category) {
        if (!adminToken.equals(authorization)) {
            return ResponseDto.of("권한이 없습니다.");
        }

        collectAnnouncementByCategory(Category.of(category));
        return ResponseDto.of("공지 수집 완료");
    }

    private void collectAnnouncementByCategory(Category category) {
        switch (category) {
            case STUDENT_NEWS -> announcementCollectScheduleService.scheduleStudentNewsProduce();
            case DORMITORY -> announcementCollectScheduleService.schedulingDormitoryAnnouncementProduce();
            case LIBRARY -> announcementCollectScheduleService.schedulingLibraryAnnouncementProduce();
            case RECRUITMENT -> announcementCollectScheduleService.scheduleRecruitmentNewsProduce();
        }
    }
}
