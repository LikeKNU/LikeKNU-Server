package com.woopaca.likeknu.collector.bus.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class NaverRealtimeBusInformation {

    private Long id;
    @JsonAlias(value = "name")
    private String busNumber;
    @JsonAlias(value = "longName")
    private String busName;
    @JsonAlias(value = "arrival")
    private ArrivalInformation arrivalInformation;

    public List<ArrivalBus> getArrivalBuses() {
        return arrivalInformation.getArrivalBuses();
    }

    @ToString
    @Getter
    public static class ArrivalInformation {

        @JsonAlias(value = "buses")
        private List<ArrivalBus> arrivalBuses;
    }
}
