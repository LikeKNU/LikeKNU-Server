package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.collector.menu.scheduler.MenuSchedulingService;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RemoteCollectController {

    private final MenuSchedulingService menuSchedulingService;

    @Value("${admin.token}")
    private String adminToken;

    public RemoteCollectController(MenuSchedulingService menuSchedulingService) {
        this.menuSchedulingService = menuSchedulingService;
    }

    @PostMapping("/collect/v1/menus")
    public ResponseDto<String> collectMenu(@RequestHeader("Authorization") String authorization) {
        if (!adminToken.equals(authorization)) {
            return ResponseDto.of("권한이 없습니다.");
        }

        menuSchedulingService.scheduleMenuProduce();
        return ResponseDto.of("메뉴 수집 완료");
    }
}
