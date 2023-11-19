package ac.knu.likeknu.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@Getter
@Embeddable
public class ShuttleTime {

    private int sequence;

    private String arrivalStop;

    private LocalTime arrivalTime;

    protected ShuttleTime() {
    }

    public ShuttleTime(int sequence, String arrivalStop, LocalTime arrivalTime) {
        this.sequence = sequence;
        this.arrivalStop = arrivalStop;
        this.arrivalTime = arrivalTime;
    }
}
