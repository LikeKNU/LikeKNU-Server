package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleListResponse;
import ac.knu.likeknu.domain.Shuttle;
import ac.knu.likeknu.domain.ShuttleBus;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.ShuttleBusRepository;
import ac.knu.likeknu.repository.ShuttleRepository;
import ac.knu.likeknu.utils.DateTimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Transactional(readOnly = true)
@Service
public class ShuttleBusService {

    private final ShuttleRepository shuttleRepository;
    private final ShuttleBusRepository shuttleBusRepository;

    public ShuttleBusService(ShuttleRepository shuttleRepository, ShuttleBusRepository shuttleBusRepository) {
        this.shuttleRepository = shuttleRepository;
        this.shuttleBusRepository = shuttleBusRepository;
    }

    /**
     * 셔틀버스 경로 목록 조회
     *
     * @param campus      캠퍼스
     * @return 캠퍼스별 셔틀버스 경로 목록
     */
    public List<ShuttleListResponse> getRouteList(Campus campus) {
        List<Shuttle> shuttles = shuttleRepository.findByCampusesContains(campus);
        shuttles.sort(Comparator.comparing(Shuttle::getSequence));

        List<ShuttleListResponse> routeList = new ArrayList<>();
        for (Shuttle shuttle : shuttles) {
            LocalDateTime nextDepartureDateTime = shuttle.getNextDepartureDateTime();
            String departureTimeMessage = generateDepartureTimeMessage(nextDepartureDateTime);

            ShuttleListResponse shuttleListResponse = ShuttleListResponse.of(shuttle, departureTimeMessage);
            routeList.add(shuttleListResponse);
        }

        return routeList;
    }

    private String generateDepartureTimeMessage(LocalDateTime nextDepartureDateTime) {
        StringBuilder message = new StringBuilder();
        String dateMessage = determineDateMessage(nextDepartureDateTime.toLocalDate());
        message.append(dateMessage)
                .append(" ");

        String timeMessage = determineTimeMessage(nextDepartureDateTime.toLocalTime());
        message.append(timeMessage);
        return message.toString();
    }

    private String determineDateMessage(LocalDate nextDepartureDate) {
        LocalDate currentDate = LocalDate.now();
        if (nextDepartureDate.isEqual(currentDate)) {
            return "";
        }

        if (nextDepartureDate.minusDays(1).isEqual(currentDate)) {
            return "내일";
        }

        String displayName = nextDepartureDate.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREA);
        if (DateTimeUtils.isAnotherWeek(nextDepartureDate, currentDate)) {
            return "다음주 " + displayName;
        }
        return displayName;
    }

    private String determineTimeMessage(LocalTime nextDepartureTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREA);
        String formattedTime = nextDepartureTime.format(formatter);
        if (formattedTime.contains(" 0분")) {
            return formattedTime.split(" 0분")[0];
        }
        return formattedTime;
    }

    /**
     * 셔틀버스 경유지 및 도착 시간 조회
     *
     * @param shuttleId 셔틀 ID
     * @return 특정 셔틀의 버스 목록과 경유지 및 도착 시간 정보
     */
    public List<ShuttleBusesArrivalTimeResponse> getShuttleBusesArrivalTime(String shuttleId) {
        Shuttle shuttle = shuttleRepository.findById(shuttleId)
                .orElseThrow(() -> new BusinessException(String.format("Shuttle not found [%s]", shuttleId)));
        return shuttleBusRepository.findByShuttle(shuttle)
                .stream()
                .sorted(Comparator.comparing(ShuttleBus::getDepartureTime))
                .map(ShuttleBusesArrivalTimeResponse::of)
                .toList();
    }
}
