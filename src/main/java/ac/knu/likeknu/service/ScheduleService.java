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
        return null;
    }
}
