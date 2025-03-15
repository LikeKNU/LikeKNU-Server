package com.woopaca.likeknu.collector.bus.dto;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Stream;

public record BusArrivalTimes(@Validated List<BusArrivalTime> busArrivalTimes) {

    public Stream<BusArrivalTime> stream() {
        return busArrivalTimes.stream();
    }
}
