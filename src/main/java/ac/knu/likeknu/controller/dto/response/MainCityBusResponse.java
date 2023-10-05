package ac.knu.likeknu.controller.dto.response;

import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MainCityBusResponse {

    private final String routeId;
    private final String departureStop;
    private final String arrivalStop;
    private final String busNumber;
    private final String remainingTime;
    private final String arrivalTime;
    private final String busColor;

    @Builder
    public MainCityBusResponse(String routeId, String departureStop, String arrivalStop, String busNumber, String remainingTime, String arrivalTime, String busColor) {
        this.routeId = routeId;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.busNumber = busNumber;
        this.remainingTime = remainingTime;
        this.arrivalTime = arrivalTime;
        this.busColor = busColor;
    }

    public static MainCityBusResponse of(Route route) {
        return MainCityBusResponse.builder()
                .routeId(route.getId())
                .departureStop(route.getDepartureStop())
                .arrivalStop(route.getArrivalStop())
                .build();
    }

    public static MainCityBusResponse of(Route route, CityBus cityBus) {
        LocalTime earliestArrivalTime = cityBus.getEarliestArrivalTime();
        long remainingTime = Duration.between(LocalTime.now(), earliestArrivalTime).toMinutes();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return MainCityBusResponse.builder()
                .routeId(route.getId())
                .departureStop(route.getDepartureStop())
                .arrivalStop(route.getArrivalStop())
                .busNumber(cityBus.getBusNumber())
                .remainingTime(remainingTime <= 1 ? "곧 도착" : remainingTime + "분 뒤")
                .arrivalTime(earliestArrivalTime.format(dateTimeFormatter))
                .busColor(cityBus.getBusColor())
                .build();
    }
}
