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
    private final String remainingTime;
    private final String arrivalTime;
    private final String busColor;

    @JsonIgnore
    private final LocalTime arrivalAt;

    @Builder
    public CityBusesArrivalTimeResponse(String busNumber, String remainingTime, String arrivalTime, String busColor, LocalTime arrivalAt) {
        this.busNumber = busNumber;
        this.remainingTime = remainingTime;
        this.arrivalTime = arrivalTime;
        this.busColor = busColor;
        this.arrivalAt = arrivalAt;
    }

    public void updateArrivalId(int sequence) {
        this.arrivalId = sequence;
    }

    public static CityBusesArrivalTimeResponse of(CityBus cityBus, LocalTime arrivalTime, LocalTime currentTime) {
        long remainingTime = Duration.between(currentTime, arrivalTime).toMinutes();
        String remainingStatus;
        if (remainingTime <= 1) {
            remainingStatus = "곧 도착";
        } else {
            remainingStatus = remainingTime + "분 뒤";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return CityBusesArrivalTimeResponse.builder()
                .busNumber(cityBus.getBusNumber())
                .arrivalTime(arrivalTime.format(dateTimeFormatter))
                .remainingTime(remainingStatus)
                .busColor(cityBus.getBusColor())
                .arrivalAt(arrivalTime)
                .build();
    }
}
