package ac.knu.likeknu.collector.announcement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "announcement")
public class AnnouncementProperties {

    private String kongjuUnivAddress;
    private String studentNews;
    private String libraryNotice;
    private String libraryFront;
    private String libraryBack;
    private String internship;
}
