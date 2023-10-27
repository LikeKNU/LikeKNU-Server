package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.schedule.ScheduleListDto;
import ac.knu.likeknu.controller.dto.schedule.ScheduleResponse;
import ac.knu.likeknu.domain.AcademicCalendar;
import ac.knu.likeknu.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final AcademicCalendarRepository academicCalendarRepository;

    public List<ScheduleResponse> getScheduleResponsesOverAPeriodOfTime() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate endDate = startDate.plusMonths(7);
        endDate = endDate.withDayOfMonth(endDate.lengthOfMonth());

        Map<LocalDate, List<ScheduleListDto>> list = academicCalendarRepository.findByStartDateBetween(startDate, endDate).stream()
                .sorted(Comparator.comparing(AcademicCalendar::getStartDate))
                .collect(Collectors.groupingBy(
                        academicCalendar -> academicCalendar.getStartDate().withDayOfMonth(1),
                        Collectors.mapping(ScheduleListDto::of, Collectors.toList())));

        List<ScheduleResponse> response = list.entrySet().stream()
                .map(entry -> ScheduleResponse.of(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ScheduleResponse::getLocalDate))
                .collect(Collectors.toList());

        response.stream().findFirst().ifPresent(sr -> sr.setScheduleCriterionWithYear(startDate));

        return response;
    }
}
