package ac.knu.likeknu.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainScheduleResponse {

    private String scheduleId;
    private String scheduleContents;
    private String scheduleDate;
    private boolean today;

    @Builder
    public MainScheduleResponse(String scheduleId, String scheduleContents, String scheduleDate, boolean today) {
        this.scheduleId = scheduleId;
        this.scheduleContents = scheduleContents;
        this.scheduleDate = scheduleDate;
        this.today = today;
    }
}
