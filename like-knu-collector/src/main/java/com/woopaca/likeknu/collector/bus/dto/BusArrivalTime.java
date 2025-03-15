package com.woopaca.likeknu.collector.bus.dto;

import com.woopaca.likeknu.collector.bus.DepartureStop;
import com.woopaca.likeknu.collector.bus.dto.KakaoRealtimeBusInformation.ArrivalInformation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record BusArrivalTime(@NotBlank String busName, @NotNull DepartureStop departureStop,
                             @NotNull LocalTime arrivalTime) {

    public static BusArrivalTime from(NaverRealtimeBusInformation realtimeBusInformation, ArrivalBus arrivalBus, DepartureStop departureStop) {
        int remainingTime = arrivalBus.getRemainingTime();
        LocalTime arrivalTime = LocalTime.now().plusSeconds(remainingTime);
        return BusArrivalTime.builder()
                .busName(realtimeBusInformation.getBusName())
                .departureStop(departureStop)
                .arrivalTime(arrivalTime)
                .build();
    }

    public static BusArrivalTime from(ArrivalInformation arrivalInformation, ArrivalBus arrivalBus, DepartureStop departureStop) {
        int remainingTime = arrivalBus.getRemainingTime();
        LocalTime arrivalTime = LocalTime.now().plusSeconds(remainingTime);
        return BusArrivalTime.builder()
                .busName(arrivalInformation.getBusName())
                .departureStop(departureStop)
                .arrivalTime(arrivalTime)
                .build();
    }
}
