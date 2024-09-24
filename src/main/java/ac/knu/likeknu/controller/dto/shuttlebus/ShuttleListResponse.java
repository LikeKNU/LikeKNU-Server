package ac.knu.likeknu.controller.dto.shuttlebus;

import ac.knu.likeknu.domain.Shuttle;
import lombok.Builder;

@Builder
public record ShuttleListResponse(String shuttleId, String shuttleName, String origin, String destination, String note,
                                  String nextDepartureTime) {

    private static final String ROUTE_NAME_DELIMITER = " → ";

    public static ShuttleListResponse of(Shuttle shuttle, String nextDepartureTime) {
        String origin = shuttle.getOrigin();
        String destination = shuttle.getDestination();
        return ShuttleListResponse.builder()
                .shuttleId(shuttle.getId())
                .shuttleName(String.join(ROUTE_NAME_DELIMITER, origin, destination))
                .origin(origin)
                .destination(destination)
                .note(shuttle.getNote())
                .nextDepartureTime(nextDepartureTime)
                .build();
    }
}
