package ac.knu.likeknu.controller.dto.citybus;

import ac.knu.likeknu.domain.CityBus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CityBusesArrivalTimeResponse {

    private int arrivalId;
    private final String busNumber;
    private String remainingTime;
    private final String arrivalTime;
    private final String busColor;

    @JsonIgnore
    private final LocalTime arrivalAt;

    @Builder
    public CityBusesArrivalTimeResponse(String busNumber, String arrivalTime, String busColor, LocalTime arrivalAt) {
        this.busNumber = busNumber;
        this.arrivalTime = arrivalTime;
        this.busColor = busColor;
        this.arrivalAt = arrivalAt;
    }

    public void updateArrivalId(int sequence) {
        this.arrivalId = sequence;
    }

    public CityBusesArrivalTimeResponse updateRemainingTime(LocalTime currentTime) {
        long remainingTime = Duration.between(currentTime, arrivalAt).toMinutes();
        if (remainingTime <= 1) {
            this.remainingTime = "곧 도착";
        } else {
            this.remainingTime = remainingTime + "분 뒤";
        }

        return this;
    }

    public static CityBusesArrivalTimeResponse of(CityBus cityBus, LocalTime arrivalTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return CityBusesArrivalTimeResponse.builder()
                .busNumber(cityBus.getBusNumber())
                .arrivalTime(arrivalTime.format(dateTimeFormatter))
                .busColor(cityBus.getBusColor())
                .arrivalAt(arrivalTime)
                .build();
    }
}
