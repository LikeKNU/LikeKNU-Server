package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.controller.dto.schedule.ScheduleResponse;
import com.woopaca.likeknu.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseDto<List<ScheduleResponse>> getSchedule() {
        List<ScheduleResponse> responses = scheduleService.getScheduleResponses();
        return ResponseDto.of(responses);
    }

}
