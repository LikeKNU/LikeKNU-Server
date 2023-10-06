package ac.knu.likeknu.controller.dto.citybus;

import ac.knu.likeknu.domain.Route;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RouteListResponse {

    private final String routeId;
    private final String origin;
    private final String destination;

    @Builder
    public RouteListResponse(String routeId, String origin, String destination) {
        this.routeId = routeId;
        this.origin = origin;
        this.destination = destination;
    }

    public static RouteListResponse of(Route route) {
        return RouteListResponse.builder()
                .routeId(route.getId())
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .build();
    }
}
