package ac.knu.likeknu.job.citybus.service;

import ac.knu.likeknu.collector.bus.DepartureStop;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.job.citybus.dto.BusArrivalTimeMessage;
import ac.knu.likeknu.repository.CityBusRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("jobCityBusService")
public class CityBusService {

    private final CityBusRepository cityBusRepository;

    public CityBusService(CityBusRepository cityBusRepository) {
        this.cityBusRepository = cityBusRepository;
    }

    public void updateRealtimeBusArrivalTime(@Valid List<BusArrivalTimeMessage> busArrivalTimes) {
        Map<DepartureStop, List<BusArrivalTimeMessage>> busArrivalTimesMap = busArrivalTimes.stream()
                .collect(Collectors.groupingBy(BusArrivalTimeMessage::getDepartureStop));
        busArrivalTimesMap.forEach(this::updateBusArrivalTimes);
    }

    private void updateBusArrivalTimes(DepartureStop departureStop, List<BusArrivalTimeMessage> busArrivalTimes) {
        Map<String, List<BusArrivalTimeMessage>> busArrivalTimesMap = busArrivalTimes.stream()
                .collect(Collectors.groupingBy(BusArrivalTimeMessage::getBusName));

        List<CityBus> cityBuses = cityBusRepository.findByBusStop(departureStop.getStopName());
        cityBuses.forEach(cityBus -> updateArrivalTimes(cityBus, busArrivalTimesMap));
    }

    private void updateArrivalTimes(CityBus cityBus, Map<String, List<BusArrivalTimeMessage>> busArrivalTimesMap) {
        String busName = cityBus.getBusName();
        Set<LocalTime> arrivalTimes = busArrivalTimesMap.getOrDefault(busName, Collections.emptyList())
                .stream()
                .map(BusArrivalTimeMessage::getArrivalTime)
                .collect(Collectors.toSet());
        cityBus.updateArrivalTimes(arrivalTimes);

        try {
            cityBusRepository.save(cityBus);
        } catch (DataIntegrityViolationException e) {
            log.error("message = {}", e.getMessage());
        }
    }
}
