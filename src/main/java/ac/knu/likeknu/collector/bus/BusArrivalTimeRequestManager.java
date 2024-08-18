package ac.knu.likeknu.collector.bus;

import ac.knu.likeknu.collector.bus.dto.KakaoRealtimeBusInformation;
import ac.knu.likeknu.collector.bus.dto.NaverRealtimeBusInformation;
import ac.knu.likeknu.collector.calendar.WebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Component
public class BusArrivalTimeRequestManager {

    private final WebProperties webProperties;
    private final RestClient restClient;

    public BusArrivalTimeRequestManager(WebProperties webProperties, RestClient restClient) {
        this.webProperties = webProperties;
        this.restClient = restClient;
    }

    public List<NaverRealtimeBusInformation> fetchNaverRealTimeBusInformation(DepartureStop departureStop) {
        String uri = generateUri(webProperties.getNaverMapBus(), "stopId", departureStop.getStopId());
        List<NaverRealtimeBusInformation> responseBody = restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        validateResponseBodyIsNotNull(responseBody);
        return responseBody.stream()
                .filter(naverRealtimeBusInformation -> !naverRealtimeBusInformation.getArrivalBuses().isEmpty())
                .toList();
    }

    public KakaoRealtimeBusInformation fetchKakaoRealTimeBusInformation(DepartureStop departureStop) {
        String uri = generateUri(webProperties.getKakaoMapBus(), "busstopid", departureStop.getStopId());
        KakaoRealtimeBusInformation responseBody = restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        validateResponseBodyIsNotNull(responseBody);
        return responseBody;
    }

    private String generateUri(String url, String parameterKey, String parameterValue) {
        return UriComponentsBuilder.fromUriString(url)
                .queryParam(parameterKey, parameterValue)
                .toUriString();
    }

    private void validateResponseBodyIsNotNull(Object responseBody) {
        if (responseBody == null) {
            log.error("[{}] Response body is null!", getClass().getName());
            throw new IllegalArgumentException(String.format("[%s] Response body is null!", getClass().getName()));
        }
    }
}
