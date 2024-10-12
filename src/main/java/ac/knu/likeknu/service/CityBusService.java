package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.citybus.CityBusRoutesResponse;
import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.CityBusesResponse;
import ac.knu.likeknu.controller.dto.citybus.MainCityBusResponse;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.RouteType;
import ac.knu.likeknu.repository.CityBusRepository;
import ac.knu.likeknu.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@Service
public class CityBusService {

    private static final int MAX_MAIN_ROUTES_SIZE = 3;
    private static final int MAX_ARRIVAL_TIMES_SIZE = 5;

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
        RouteType routeType = RouteType.determineRouteType(LocalTime.now());
        return routeRepository.findByCampus(campus)
                .stream()
                .filter(route -> route.getRouteType().equals(routeType))
                .sorted(Comparator.comparing(Route::getSequence))
                .map(this::generateMainCityBusResponse)
                .limit(MAX_MAIN_ROUTES_SIZE)
                .toList();
    }

    private MainCityBusResponse generateMainCityBusResponse(Route route) {
        return findEarliestCityBus(cityBusRepository.findByRoutesContaining(route))
                .map(earliestCityBus -> MainCityBusResponse.of(route, earliestCityBus))
                .orElse(MainCityBusResponse.empty(route));
    }

    private Optional<CityBus> findEarliestCityBus(List<CityBus> buses) {
        return buses.stream()
                .filter(cityBus -> cityBus.getEarliestArrivalTime() != null)
                .min(Comparator.comparing(CityBus::getEarliestArrivalTime));
    }

    /**
     * 특정 경로의 시내버스 도착 시간 조회
     *
     * @param campus    캠퍼스
     * @param routeType 경로 종류(들어오는 거, 나가는 거)
     * @return 특정 경로의 시내버스 도착 시간 목록
     */
    public List<CityBusesResponse> getCityBusesArrivalTime(Campus campus, RouteType routeType) {
        return routeRepository.findByCampusAndRouteType(campus, routeType)
                .stream()
                .sorted(Comparator.comparing(Route::getSequence))
                .map(this::generateCityBusesResponse)
                .toList();
    }

    /**
     * 특정 경로의 시내버스 도착 시간 조회
     * @param routeId 경로 ID
     * @return 특정 경로의 시내버스 도착 시간 목록
     */
    public CityBusesResponse getCityBusesArrivalTime(String routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경로입니다."));
        List<CityBusesArrivalTimeResponse> list = cityBusRepository.findByRoutesContaining(route)
                .stream()
                .flatMap(cityBus -> cityBus.getArrivalTimes()
                        .stream()
                        .map(arrivalTime -> CityBusesArrivalTimeResponse.of(cityBus, arrivalTime, LocalTime.now())))
                .sorted(Comparator.comparing(CityBusesArrivalTimeResponse::arrivalAt))
                .toList();
        return CityBusesResponse.of(route, list);
    }

    public List<CityBusRoutesResponse> getRouteList(Campus campus) {
        LocalTime currentTime = LocalTime.now();
        return routeRepository.findByCampus(campus)
                .stream()
                .sorted(Comparator.comparing(Route::getSequence))
                .map(route -> {
                    LocalTime nextArrivalTime = cityBusRepository.findByRoutesContaining(route)
                            .stream()
                            .flatMap(cityBus -> cityBus.getArrivalTimes().stream())
                            .filter(currentTime::isBefore)
                            .min(Comparator.naturalOrder())
                            .orElse(null);
                    return CityBusRoutesResponse.of(route, nextArrivalTime);
                })
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
        return cityBus.getArrivalTimes()
                .stream()
                .filter(currentTime.minusMinutes(1)::isBefore)
                .filter(currentTime.plusMinutes(30)::isAfter);
    }
}
