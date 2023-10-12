package ac.knu.likeknu.controller.dto.citybus;

import ac.knu.likeknu.domain.Route;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RouteListResponse {

    private static final String ROUTE_NAME_DELIMITER = " → ";

    private final String routeId;
    private final String routeName;
    private final String departureStop;
    private final String arrivalStop;

    @Builder
    public RouteListResponse(String routeId, String routeName, String departureStop, String arrivalStop) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
    }

    public static RouteListResponse of(Route route) {
        String origin = route.getOrigin();
        String destination = route.getDestination();
        return RouteListResponse.builder()
                .routeId(route.getId())
                .routeName(String.join(ROUTE_NAME_DELIMITER, origin, destination))
                .departureStop(route.getDepartureStop())
                .arrivalStop(route.getArrivalStop())
                .build();
    }
}
