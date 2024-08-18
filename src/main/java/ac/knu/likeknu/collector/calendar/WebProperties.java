package ac.knu.likeknu.collector.calendar;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "url")
@Component
public class WebProperties {

    private String academicCalendar;
    private String naverMapBus;
    private String dormitoryAnnouncement;
    private String kakaoMapBus;
}
