package ac.knu.likeknu.collector.bus.scheduler;

import ac.knu.likeknu.collector.bus.dto.BusArrivalTime;
import ac.knu.likeknu.collector.bus.dto.BusArrivalTimes;
import ac.knu.likeknu.collector.event.EventProducer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusArrivalTimeProducer {

    private final EventProducer<BusArrivalTimes> producer;

    public BusArrivalTimeProducer(EventProducer<BusArrivalTimes> producer) {
        this.producer = producer;
    }

    public void produce(List<BusArrivalTime> busArrivalTimes) {
        producer.produce(new BusArrivalTimes(busArrivalTimes));
    }
}
