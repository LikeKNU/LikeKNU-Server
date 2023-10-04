package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.controller.dto.response.MainCityBusResponse;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.RouteType;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.CityBusRepository;
import ac.knu.likeknu.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CityBusService {

    private final RouteRepository routeRepository;
    private final CityBusRepository cityBusRepository;

    public CityBusService(RouteRepository routeRepository, CityBusRepository cityBusRepository) {
        this.routeRepository = routeRepository;
        this.cityBusRepository = cityBusRepository;
    }

    public List<MainCityBusResponse> earliestOutgoingCityBuses(Campus campus) {
        return routeRepository.findByCampus(campus, Sort.unsorted()).stream()
                .filter(route -> route.getRouteType().equals(RouteType.OUTGOING))
                .map(route -> {
                    List<CityBus> buses = cityBusRepository.findByRoutesContaining(route);
                    CityBus earliestBus = getEarliestBus(buses);
                    if (earliestBus == null) {
                        return MainCityBusResponse.of(route);
                    }
                    return MainCityBusResponse.of(route, earliestBus);
                })
                .toList();
    }

    private CityBus getEarliestBus(List<CityBus> buses) {
        return buses.stream()
                .filter(cityBus -> cityBus.getEarliestArrivalTime() != null)
                .min(Comparator.comparing(CityBus::getEarliestArrivalTime))
                .orElse(null);
    }

    public List<RouteListResponse> getRouteList(Campus campus) {
        return routeRepository.findByCampus(campus, Sort.by(
                        Order.desc("routeType"), Order.asc("origin")
                )).stream()
                .map(RouteListResponse::of)
                .toList();
    }

    public List<CityBusesArrivalTimeResponse> getCityBusesArrivalTime(String routeId) {
        List<CityBus> buses = getCityBusesOfRoute(routeId);

        List<CityBusesArrivalTimeResponse> cityBusesArrivalTime = getCityBusesArrivalTime(buses);
        IntStream.rangeClosed(1, cityBusesArrivalTime.size())
                .forEach(sequence -> cityBusesArrivalTime.get(sequence - 1).updateArrivalId(sequence));

        return cityBusesArrivalTime;
    }

    private List<CityBus> getCityBusesOfRoute(String routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new BusinessException(String.format("Route not found [%s]", routeId)));
        return cityBusRepository.findByRoutesContaining(route);
    }

    private List<CityBusesArrivalTimeResponse> getCityBusesArrivalTime(List<CityBus> buses) {
        LocalTime currentTime = LocalTime.now();
        LocalTime minimumTime = currentTime.minusMinutes(1);
        LocalTime maximumTime = currentTime.plusMinutes(60);

        return buses.stream()
                .flatMap(cityBus -> cityBus.getArrivalTimes().stream()
                        .filter(minimumTime::isBefore)
                        .filter(maximumTime::isAfter)
                        .map(arrivalTime -> CityBusesArrivalTimeResponse.of(cityBus, arrivalTime))
                        .map(cityBusArrivalTime -> cityBusArrivalTime.updateRemainingTime(currentTime)))
                .sorted(Comparator.comparing(CityBusesArrivalTimeResponse::getArrivalAt))
                .toList();
    }
}
