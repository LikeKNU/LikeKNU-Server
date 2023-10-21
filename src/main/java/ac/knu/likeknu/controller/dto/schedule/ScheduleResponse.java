package ac.knu.likeknu.controller.dto.schedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ScheduleResponse {

    private String scheduleCriterion;
    private List<ScheduleListDto> ScheduleWrapper;

    @Builder
    public ScheduleResponse(String scheduleCriterion, List<ScheduleListDto> scheduleWrapper) {
        this.scheduleCriterion = scheduleCriterion;
        ScheduleWrapper = scheduleWrapper;
    }

    public static ScheduleResponse of(LocalDate date, List<ScheduleListDto> scheduleWrapper) {
        return ScheduleResponse.builder()
                .scheduleCriterion(generateCriterion(date))
                .scheduleWrapper(scheduleWrapper)
                .build();
    }

    private static String generateCriterion(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(
                date.getMonthValue() == 1 ? "yyyy년 MM월" : "MM월"
        ));
    }
}
