package ac.knu.likeknu.job.citybus.service;

import ac.knu.likeknu.collector.bus.dto.BusArrivalTimes;
import ac.knu.likeknu.job.citybus.dto.BusArrivalTimeMessage;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusArrivalTimeConsumer {

    private final CityBusService cityBusService;

    public BusArrivalTimeConsumer(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    @EventListener
    public void consumeBusArrivalTimeMessage(@Valid BusArrivalTimes busArrivalTimes) {
        List<BusArrivalTimeMessage> busArrivalTimesMessage = busArrivalTimes.stream()
                .map(BusArrivalTimeMessage::from)
                .toList();
        cityBusService.updateRealtimeBusArrivalTime(busArrivalTimesMessage);
    }
}
