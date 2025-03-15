package com.woopaca.likeknu.controller.dto.citybus;

import com.woopaca.likeknu.entity.CityBus;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalTime;

@Builder
public record CityBusesArrivalTimeResponse(String busNumber, String remainingTime, String busColor,
                                           LocalTime arrivalAt) {

    public static CityBusesArrivalTimeResponse of(CityBus cityBus, LocalTime arrivalTime, LocalTime currentTime) {
        long remainingTime = Duration.between(currentTime, arrivalTime).toMinutes();
        String remainingStatus = determineRemainingStatusMessage(remainingTime);
        return CityBusesArrivalTimeResponse.builder()
                .busNumber(cityBus.getBusNumber())
                .remainingTime(remainingStatus)
                .busColor(cityBus.getBusColor())
                .arrivalAt(arrivalTime)
                .build();
    }

    private static String determineRemainingStatusMessage(long remainingTime) {
        if (remainingTime < 0) {
            return null;
        }
        return remainingTime <= 1 ? "곧 도착" : remainingTime + "분";
    }
}
