package ac.knu.likeknu.controller.dto.citybus;

import ac.knu.likeknu.domain.Route;

import java.time.LocalTime;

public record CityBusRoutesResponse(String routeId, String origin, String destination, LocalTime nextArrivalTime) {

    public static CityBusRoutesResponse of(Route route, LocalTime nextArrivalTime) {
        return new CityBusRoutesResponse(route.getId(), route.getOrigin(), route.getDestination(), nextArrivalTime);
    }
}
