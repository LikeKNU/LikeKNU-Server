package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.CityBusesResponse;
import ac.knu.likeknu.controller.dto.main.MainCityBusResponse;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.RouteType;
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
import java.util.stream.Stream;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CityBusService {

    private static final int MAX_MAIN_ROUTES_SIZE = 3;
    private static final int MAX_ARRIVAL_TIMES_SIZE = 5;
    private static final LocalTime ROUTE_TYPE_CHANGE_TIME = LocalTime.of(12, 0);

    private final RouteRepository routeRepository;
    private final CityBusRepository cityBusRepository;

    public CityBusService(RouteRepository routeRepository, CityBusRepository cityBusRepository) {
        this.routeRepository = routeRepository;
        this.cityBusRepository = cityBusRepository;
    }

    /**
     * 학교에서 출발하거나 돌아오는 경로 중 가장 금방 도착하는 시내버스 정보
     *
     * @param campus 캠퍼스
     * @return 캠퍼스별 학교에서 출발하거나 돌아오는 경로의 가장 빠른 시내버스 목록
     */
    public List<MainCityBusResponse> earliestArriveCityBuses(Campus campus) {
        RouteType routeType = getRouteType(LocalTime.now());
        return routeRepository.findByCampus(campus, Sort.by(Order.asc("origin"))).stream()
                .filter(route -> route.getRouteType().equals(routeType))
                .sorted(Comparator.comparing(Route::getSequence))
                .map(this::generateMainCityBusResponse)
                .limit(MAX_MAIN_ROUTES_SIZE)
                .toList();
    }

    private RouteType getRouteType(LocalTime time) {
        if (time.isBefore(ROUTE_TYPE_CHANGE_TIME)) {
            return RouteType.INCOMING;
        }
        return RouteType.OUTGOING;
    }

    private MainCityBusResponse generateMainCityBusResponse(Route route) {
        CityBus earliestBus = getEarliestCityBus(cityBusRepository.findByRoutesContaining(route));
        if (earliestBus == null) {
            return MainCityBusResponse.empty(route);
        }
        return MainCityBusResponse.of(route, earliestBus);
    }

    private CityBus getEarliestCityBus(List<CityBus> buses) {
        return buses.stream()
                .filter(cityBus -> cityBus.getEarliestArrivalTime() != null)
                .min(Comparator.comparing(CityBus::getEarliestArrivalTime))
                .orElse(null);
    }

    /**
     * 특정 경로의 시내버스 도착 시간 조회
     *
     * @param campus    캠퍼스
     * @param routeType 경로 종류(들어오는 거, 나가는 거)
     * @return 특정 경로의 시내버스 도착 시간 목록
     */
    public List<CityBusesResponse> getCityBusesArrivalTime(Campus campus, RouteType routeType) {
        List<Route> routes = routeRepository.findByCampusAndRouteType(campus, routeType);
        return routes.stream()
                .sorted(Comparator.comparing(Route::getSequence))
                .map(this::generateCityBusesResponse)
                .toList();
    }

    private CityBusesResponse generateCityBusesResponse(Route route) {
        List<CityBusesArrivalTimeResponse> cityBusesArrivalTime = getCityBusesArrivalTime(route);
        return CityBusesResponse.of(route, cityBusesArrivalTime);
    }

    private List<CityBusesArrivalTimeResponse> getCityBusesArrivalTime(Route route) {
        LocalTime currentTime = LocalTime.now();
        return cityBusRepository.findByRoutesContaining(route)
                .stream()
                .flatMap(cityBus -> getCloseArrivalTimesStream(cityBus, currentTime)
                        .map(arrivalTime -> CityBusesArrivalTimeResponse.of(cityBus, arrivalTime, currentTime)))
                .sorted(Comparator.comparing(CityBusesArrivalTimeResponse::arrivalAt))
                .limit(MAX_ARRIVAL_TIMES_SIZE)
                .toList();
    }

    private Stream<LocalTime> getCloseArrivalTimesStream(CityBus cityBus, LocalTime currentTime) {
        return cityBus.getArrivalTimes().stream()
                .filter(currentTime.minusMinutes(1)::isBefore)
                .filter(currentTime.plusMinutes(30)::isAfter);
    }
}
