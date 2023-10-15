package ac.knu.likeknu.controller.dto.shuttlebus;

import ac.knu.likeknu.domain.Shuttle;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShuttleListResponse {

    private static final String ROUTE_NAME_DELIMITER = " → ";

    private final String shuttleId;
    private final String shuttleName;
    private final String note;

    @Builder
    public ShuttleListResponse(String shuttleId, String shuttleName, String note) {
        this.shuttleId = shuttleId;
        this.shuttleName = shuttleName;
        this.note = note;
    }

    public static ShuttleListResponse of(Shuttle shuttle) {
        String origin = shuttle.getOrigin();
        String destination = shuttle.getDestination();
        return ShuttleListResponse.builder()
                .shuttleId(shuttle.getId())
                .shuttleName(String.join(ROUTE_NAME_DELIMITER, origin, destination))
                .note(shuttle.getNote())
                .build();
    }
}
