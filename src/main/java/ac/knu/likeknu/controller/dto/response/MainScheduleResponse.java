package ac.knu.likeknu.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainScheduleResponse {

    private String scheduleId;
    private String scheduleContents;
    private boolean today;

    @Builder
    public MainScheduleResponse(String scheduleId, String scheduleContents, boolean today) {
        this.scheduleId = scheduleId;
        this.scheduleContents = scheduleContents;
        this.today = today;
    }
}
