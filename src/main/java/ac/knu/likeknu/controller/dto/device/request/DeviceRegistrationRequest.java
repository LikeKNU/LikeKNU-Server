package ac.knu.likeknu.controller.dto.device.request;

import ac.knu.likeknu.domain.constants.Campus;

public record DeviceRegistrationRequest(
        String deviceId, String userAgent, Campus campus, String themeColor, String favoriteCafeteria
) {
}
