package ac.knu.likeknu.collector.menu;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "web")
public class MenuProperties {

    private String cafeteriaPrefix;
    private String cafeteriaPostfix;
    private String dormitoryCafeteriaPrefix;
}
