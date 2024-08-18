package ac.knu.likeknu.job.citybus.dto;

import ac.knu.likeknu.collector.bus.DepartureStop;
import ac.knu.likeknu.collector.bus.dto.BusArrivalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@Getter
public class BusArrivalTimeMessage {

    private String busName;
    private DepartureStop departureStop;
    private LocalTime arrivalTime;

    protected BusArrivalTimeMessage() {
    }

    @Builder
    public BusArrivalTimeMessage(String busName, DepartureStop departureStop, LocalTime arrivalTime) {
        this.busName = busName;
        this.departureStop = departureStop;
        this.arrivalTime = arrivalTime;
    }

    public static BusArrivalTimeMessage from(BusArrivalTime busArrivalTime) {
        return BusArrivalTimeMessage.builder()
                .busName(busArrivalTime.busName())
                .departureStop(busArrivalTime.departureStop())
                .arrivalTime(busArrivalTime.arrivalTime())
                .build();
    }
}
