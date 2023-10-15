package ac.knu.likeknu.controller.dto.shuttlebus;

import ac.knu.likeknu.domain.ShuttleBus;
import ac.knu.likeknu.domain.ShuttleTime;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ShuttleBusesArrivalTimeResponse {

    private final String busName;
    private final List<ShuttleTimeDto> times;

    @Builder
    public ShuttleBusesArrivalTimeResponse(String busName, List<ShuttleTimeDto> times) {
        this.busName = busName;
        this.times = times;
    }

    public static ShuttleBusesArrivalTimeResponse of(ShuttleBus shuttleBus) {
        List<ShuttleTimeDto> shuttleTimeList = shuttleBus.getShuttleTimes().stream()
                .map(ShuttleTimeDto::of)
                .toList();
        return ShuttleBusesArrivalTimeResponse.builder()
                .busName(shuttleBus.getBusName())
                .times(shuttleTimeList)
                .build();
    }

    @Getter
    static class ShuttleTimeDto {

        private final int stopId;
        private final String arrivalStop;
        private final String arrivalTime;

        @Builder
        ShuttleTimeDto(int stopId, String arrivalStop, String arrivalTime) {
            this.stopId = stopId;
            this.arrivalStop = arrivalStop;
            this.arrivalTime = arrivalTime;
        }

        public static ShuttleTimeDto of(ShuttleTime shuttleTime) {
            return ShuttleTimeDto.builder()
                    .stopId(shuttleTime.getSequence())
                    .arrivalStop(shuttleTime.getArrivalStop())
                    .arrivalTime(shuttleTime.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .build();
        }
    }
}
