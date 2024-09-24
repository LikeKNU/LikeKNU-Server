package ac.knu.likeknu.controller.dto.citybus;

import ac.knu.likeknu.domain.Route;
import lombok.Builder;

import java.util.List;

@Builder
public record CityBusesResponse(String origin, String destination, String arrivalStop, String departureStop,
                                List<CityBusesArrivalTimeResponse> buses) {

    public static CityBusesResponse of(Route route, List<CityBusesArrivalTimeResponse> buses) {
        return CityBusesResponse.builder()
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .arrivalStop(route.getArrivalStop())
                .departureStop(route.getDepartureStop())
                .buses(buses)
                .build();
    }
}
