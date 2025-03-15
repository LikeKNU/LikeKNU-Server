package com.woopaca.likeknu.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class CityBuses {

    private final List<CityBus> buses;

    public CityBuses(List<CityBus> buses) {
        this.buses = buses;
    }
}
