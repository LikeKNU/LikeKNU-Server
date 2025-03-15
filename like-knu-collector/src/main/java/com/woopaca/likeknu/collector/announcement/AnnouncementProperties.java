package com.woopaca.likeknu.collector.announcement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "announcement")
public class AnnouncementProperties {

    private String kongjuUniversityUrl;
    private String studentNewsUrl;
    private String libraryAnnouncementUrl;
    private String libraryAnnouncementPrefixUrl;
    private String recruitmentNewsUrl;
}
