package ac.knu.likeknu.controller.dto.schedule;

import ac.knu.likeknu.domain.AcademicCalendar;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponse {

    private String scheduleId;
    private String scheduleContents;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;

    @Builder
    public ScheduleResponse(String scheduleId, String scheduleContents, LocalDate scheduleStartDate, LocalDate scheduleEndDate) {
        this.scheduleId = scheduleId;
        this.scheduleContents = scheduleContents;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
    }

    public static ScheduleResponse of(AcademicCalendar academicCalendar) {
        return ScheduleResponse.builder()
                .scheduleId(academicCalendar.getId())
                .scheduleContents(academicCalendar.getContents())
                .scheduleStartDate(academicCalendar.getStartDate())
                .scheduleEndDate(academicCalendar.getEndDate())
                .build();
    }
}
