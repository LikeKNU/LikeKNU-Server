package ac.knu.likeknu.controller.dto.citybus;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class CityBusesArrivalTimeResponse {

    private final int arrivalId;
    private final String busNumber;
    private final String remainingTime;
    private final String arrivalTime;
    private final String busColor;

    @JsonAlias
    private final LocalTime arrivalAt;

    @Builder
    public CityBusesArrivalTimeResponse(int arrivalId, String busNumber, String remainingTime, String arrivalTime, String busColor, LocalTime arrivalAt) {
        this.arrivalId = arrivalId;
        this.busNumber = busNumber;
        this.remainingTime = remainingTime;
        this.arrivalTime = arrivalTime;
        this.busColor = busColor;
        this.arrivalAt = arrivalAt;
    }
}
