package ac.knu.likeknu.collector.announcement.dormitory;

import ac.knu.likeknu.collector.calendar.WebProperties;
import ac.knu.likeknu.collector.menu.constants.Campus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DormitoryAnnouncementClient {

    private static final String DORMITORY_ID_KEY = "id";
    private static final String PAGE_KEY = "p";

    private final WebProperties webProperties;
    private final RestClient restClient;

    public DormitoryAnnouncementClient(WebProperties webProperties, RestClient restClient) {
        this.webProperties = webProperties;
        this.restClient = restClient;
    }

    public String fetchDormitoryAnnouncementPage(Campus campus, int page) {
        String uri = UriComponentsBuilder.fromUriString(webProperties.getDormitoryAnnouncement() + "/HOME/board/")
                .queryParam(DORMITORY_ID_KEY, campus.getDormitoryAnnouncementId())
                .queryParam(PAGE_KEY, page)
                .toUriString();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}
