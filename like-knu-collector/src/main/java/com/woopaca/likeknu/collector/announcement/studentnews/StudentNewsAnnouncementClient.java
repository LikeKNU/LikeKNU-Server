package com.woopaca.likeknu.collector.announcement.studentnews;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class StudentNewsAnnouncementClient {

    private final RestClient restClient;
    private final AnnouncementProperties announcementProperties;

    public StudentNewsAnnouncementClient(RestClient restClient, AnnouncementProperties announcementProperties) {
        this.restClient = restClient;
        this.announcementProperties = announcementProperties;
    }

    public String fetchStudentNewsAnnouncementPage() {
        String url = announcementProperties.getStudentNewsUrl();
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}
