package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseDto getSchedule(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month
    ) {
        if(areYearAndMonthNull(year, month)) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
        }

        return ResponseDto.of(null);
    }

    private boolean areYearAndMonthNull(Integer year, Integer month) {
        return year == null || month == null;
    }

}
