package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleBusesArrivalTimeResponse;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.ShuttleType;
import ac.knu.likeknu.repository.ShuttleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShuttleBusService {

    private final ShuttleRepository shuttleRepository;

    public ShuttleBusService(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
    }

    /**
     * 셔틀버스 경로 목록 조회
     *
     * @param campus      캠퍼스
     * @param shuttleType 셔틀버스 타입 (등교버스•순환버스)
     * @return 캠퍼스별 셔틀버스 경로 목록
     */
    public List<RouteListResponse> getRouteList(Campus campus, ShuttleType shuttleType) {
        return shuttleRepository.findByShuttleType(shuttleType).stream()
                .filter(shuttle -> shuttle.getCampuses().contains(campus))
                .map(RouteListResponse::of)
                .toList();
    }

    public List<ShuttleBusesArrivalTimeResponse> getShuttleBusesArrivalTime(String routeId) {
        return null;
    }
}
