package ac.knu.likeknu.collector.bus.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class KakaoRealtimeBusInformation {

    @JsonAlias(value = "lines")
    private List<ArrivalInformation> arrivals;

    public List<ArrivalInformation.ArrivalBusTime> getArrivalBuses() {
        return arrivals.stream()
                .map(ArrivalInformation::getArrivalBusTime)
                .toList();
    }

    @Getter
    public static class ArrivalInformation {

        @JsonAlias(value = "name")
        private String busName;

        @JsonAlias(value = "arrival")
        private ArrivalBusTime arrivalBusTime;

        public List<ArrivalBus> getArrivalBuses() {
            List<ArrivalBus> arrivalBuses = new ArrayList<>();
            int remainingTime = arrivalBusTime.remainingTime;
            if (remainingTime == 0) {
                return Collections.emptyList();
            }

            arrivalBuses.add(new ArrivalBus(remainingTime));
            int subRemainingTime = arrivalBusTime.subRemainingTime;
            if (subRemainingTime != 0) {
                arrivalBuses.add(new ArrivalBus(subRemainingTime));
            }
            return arrivalBuses;
        }

        @Getter
        public static class ArrivalBusTime {

            @JsonAlias(value = "arrivalTime")
            private int remainingTime;

            @JsonAlias(value = "arrivalTime2")
            private int subRemainingTime;
        }
    }
}
