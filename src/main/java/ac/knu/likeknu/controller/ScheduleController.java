package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.schedule.ScheduleResponse;
import ac.knu.likeknu.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseDto<List<ScheduleResponse>> getSchedule(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month
    ) {
        if(isYearOrMonthNull(year, month)) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
        }

        List<ScheduleResponse> responses = scheduleService.getScheduleResponsesByYearAndMonth(year, month);
        return ResponseDto.of(responses);
    }

    private boolean isYearOrMonthNull(Integer year, Integer month) {
        return year == null || month == null;
    }

}
