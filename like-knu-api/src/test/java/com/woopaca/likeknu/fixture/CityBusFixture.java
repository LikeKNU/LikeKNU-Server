package com.woopaca.likeknu.fixture;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.RouteType;
import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.entity.Route;

import java.time.LocalTime;
import java.util.List;

public final class CityBusFixture {

    public static Route createRoute() {
        return Route.builder()
                .routeType(RouteType.INCOMING)
                .departureStop("종합버스터미널")
                .arrivalStop("공주대학교")
                .origin("터미널")
                .destination("공주대")
                .campus(Campus.SINGWAN)
                .build();
    }

    public static CityBus createCityBus() {
        return CityBus.builder()
                .busNumber("123-1")
                .busName("123-1 공주대 방면")
                .busColor("95C53C")
                .busStop("종합버스터미널")
                .arrivalTimes(List.of(LocalTime.now().plusMinutes(5)))
                .build();
    }
}
