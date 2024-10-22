package ac.knu.likeknu.collector.announcement.library;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LibraryAnnouncementClient {

    private final RestClient restClient;
    private final AnnouncementProperties announcementProperties;

    public LibraryAnnouncementClient(RestClient restClient, AnnouncementProperties announcementProperties) {
        this.restClient = restClient;
        this.announcementProperties = announcementProperties;
    }

    public String fetchLibraryAnnouncement() {
        String url = announcementProperties.getLibraryAnnouncementUrl();
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}
