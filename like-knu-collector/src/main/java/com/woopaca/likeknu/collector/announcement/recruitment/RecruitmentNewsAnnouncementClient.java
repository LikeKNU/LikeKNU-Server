package com.woopaca.likeknu.collector.announcement.recruitment;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RecruitmentNewsAnnouncementClient {

    private final AnnouncementProperties announcementProperties;
    private final RestTemplate restTemplate;

    public RecruitmentNewsAnnouncementClient(AnnouncementProperties announcementProperties, RestTemplate restTemplate) {
        this.announcementProperties = announcementProperties;
        this.restTemplate = restTemplate;
    }

    public String fetchRecruitmentNewsAnnouncementPage() {
        String url = announcementProperties.getRecruitmentNewsUrl();
        return restTemplate.getForObject(url, String.class);
    }
}
