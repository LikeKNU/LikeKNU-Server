package ac.knu.likeknu.controller.dto.schedule;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleListDto {

    private String scheduleContents;
    private String scheduleDate;

    @Builder
    public ScheduleListDto(String scheduleContents, String scheduleDate) {
        this.scheduleContents = scheduleContents;
        this.scheduleDate = scheduleDate;
    }

}
