package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.schedule.ScheduleResponse;
import ac.knu.likeknu.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final AcademicCalendarRepository academicCalendarRepository;

    public List<ScheduleResponse> getScheduleResponsesByYearAndMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, 31);
        return academicCalendarRepository.findByStartDateBetweenOrEndDateBetween(start, end, start, end).stream()
                .map(ScheduleResponse::of)
                .collect(Collectors.toList());
    }
}
