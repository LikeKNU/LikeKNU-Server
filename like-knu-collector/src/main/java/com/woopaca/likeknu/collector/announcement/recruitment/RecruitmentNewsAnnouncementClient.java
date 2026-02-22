package com.woopaca.likeknu.collector.announcement.recruitment;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RecruitmentNewsAnnouncementClient {

    private final AnnouncementProperties announcementProperties;
    private final RestClient restClient;

    public RecruitmentNewsAnnouncementClient(AnnouncementProperties announcementProperties, RestClient restClient) {
        this.announcementProperties = announcementProperties;
        this.restClient = restClient;
    }

    public String fetchRecruitmentNewsAnnouncementPage() {
        String url = announcementProperties.getRecruitmentNewsUrl();
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}
