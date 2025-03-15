package com.woopaca.likeknu.controller.dto.citybus;

import com.woopaca.likeknu.entity.Route;

import java.time.LocalTime;

public record CityBusRoutesResponse(String routeId, String origin, String destination, LocalTime nextArrivalTime) {

    public static CityBusRoutesResponse of(Route route, LocalTime nextArrivalTime) {
        return new CityBusRoutesResponse(route.getId(), route.getOrigin(), route.getDestination(), nextArrivalTime);
    }
}
