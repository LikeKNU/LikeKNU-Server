package com.woopaca.likeknu.collector.announcement.studentnews;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class StudentNewsAnnouncementClient {

    private final RestClient restClient;
    private final AnnouncementProperties announcementProperties;
    private final RestTemplate restTemplate;

    public StudentNewsAnnouncementClient(RestClient restClient, AnnouncementProperties announcementProperties, RestTemplate restTemplate) {
        this.restClient = restClient;
        this.announcementProperties = announcementProperties;
        this.restTemplate = restTemplate;
    }

    public String fetchStudentNewsAnnouncementPage() {
        String url = announcementProperties.getStudentNewsUrl();
        return restTemplate.getForObject(url, String.class);
    }
}
