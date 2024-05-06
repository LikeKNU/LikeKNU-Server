package ac.knu.likeknu.controller.dto.device.request;

import ac.knu.likeknu.domain.constants.Campus;
import com.fasterxml.jackson.annotation.JsonAlias;

public record DeviceRegistrationRequest(
        String deviceId,
        @JsonAlias("platform")
        String userAgent,
        String modelName,
        String osVersion,
        Campus campus,
        String themeColor,
        String favoriteCafeteria
) {
}
