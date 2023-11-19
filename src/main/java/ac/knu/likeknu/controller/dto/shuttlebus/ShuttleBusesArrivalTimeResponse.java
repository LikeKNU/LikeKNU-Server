package ac.knu.likeknu.controller.dto.shuttlebus;

import ac.knu.likeknu.domain.ShuttleBus;
import ac.knu.likeknu.domain.ShuttleTime;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Builder
public record ShuttleBusesArrivalTimeResponse(String busName, List<ShuttleTimeDto> times) {

    public static ShuttleBusesArrivalTimeResponse of(ShuttleBus shuttleBus) {
        List<ShuttleTimeDto> shuttleTimeList = shuttleBus.getShuttleTimes()
                .stream()
                .sorted(Comparator.comparing(ShuttleTime::getArrivalTime))
                .map(ShuttleTimeDto::of)
                .toList();
        return ShuttleBusesArrivalTimeResponse.builder()
                .busName(shuttleBus.getBusName())
                .times(shuttleTimeList)
                .build();
    }

    @Builder
    record ShuttleTimeDto(String arrivalStop, String arrivalTime) {

        public static ShuttleTimeDto of(ShuttleTime shuttleTime) {
            return ShuttleTimeDto.builder()
                    .arrivalStop(shuttleTime.getArrivalStop())
                    .arrivalTime(shuttleTime.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .build();
        }
    }
}
