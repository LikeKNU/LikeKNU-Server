package ac.knu.likeknu.collector.bus.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ArrivalBus {

    private int remainingTime;

    protected ArrivalBus() {
    }

    public ArrivalBus(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
