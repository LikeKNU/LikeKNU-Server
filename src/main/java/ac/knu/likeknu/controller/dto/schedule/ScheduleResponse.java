package ac.knu.likeknu.controller.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ScheduleResponse {

    private String scheduleCriterion;
    private List<ScheduleListDto> ScheduleWrapper;

    @JsonIgnore
    private LocalDate localDate;

    @Builder
    public ScheduleResponse(String scheduleCriterion, List<ScheduleListDto> scheduleWrapper, LocalDate localDate) {
        this.scheduleCriterion = scheduleCriterion;
        this.ScheduleWrapper = scheduleWrapper;
        this.localDate = localDate;
    }

    public static ScheduleResponse of(LocalDate date, List<ScheduleListDto> scheduleWrapper) {
        return ScheduleResponse.builder()
                .scheduleCriterion(generateCriterion(date))
                .scheduleWrapper(scheduleWrapper)
                .localDate(date)
                .build();
    }

    private static String generateCriterion(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(isNextYear(date) ? "yyyy년 MM월" : "MM월"));
    }

    private static boolean isNextYear(LocalDate date) {
        return LocalDate.now().plusYears(1).getYear() == date.getYear();
    }

    public void setScheduleCriterionWithYear(LocalDate date) {
        scheduleCriterion = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월"));
    }
}
