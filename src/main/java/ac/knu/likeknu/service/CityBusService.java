package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.response.MainCityBusResponse;
import ac.knu.likeknu.controller.dto.response.RouteListResponse;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.RouteType;
import ac.knu.likeknu.repository.CityBusRepository;
import ac.knu.likeknu.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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
}
