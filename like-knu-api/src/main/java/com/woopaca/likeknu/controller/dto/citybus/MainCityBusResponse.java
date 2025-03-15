package com.woopaca.likeknu.controller.dto.citybus;

import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.entity.Route;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Builder
public record MainCityBusResponse(String routeId, String origin, String destination, String busNumber,
                                  String remainingTime, String arrivalTime, String busColor) {

    public static MainCityBusResponse empty(Route route) {
        return MainCityBusResponse.builder()
                .routeId(route.getId())
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .build();
    }

    public static MainCityBusResponse of(Route route, CityBus cityBus) {
        LocalTime earliestArrivalTime = cityBus.getEarliestArrivalTimeWithinRange();
        long remainingTime = Duration.between(LocalTime.now(), earliestArrivalTime).toMinutes();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return MainCityBusResponse.builder()
                .routeId(route.getId())
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .busNumber(cityBus.getBusNumber())
                .remainingTime(remainingTime <= 1 ? "곧 도착" : remainingTime + "분")
                .arrivalTime(earliestArrivalTime.format(dateTimeFormatter))
                .busColor(cityBus.getBusColor())
                .build();
    }
}
