package com.woopaca.likeknu.collector.bus.scheduler;

import com.woopaca.likeknu.collector.bus.dto.BusArrivalTime;
import com.woopaca.likeknu.collector.bus.dto.BusArrivalTimes;
import com.woopaca.likeknu.collector.event.EventProducer;
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
