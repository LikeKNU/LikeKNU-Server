package ac.knu.likeknu.controller.dto.device.request;

import ac.knu.likeknu.domain.constants.Campus;

public record DeviceRegistrationRequest(
        String deviceId,
        String platform,
        String modelName,
        String osVersion,
        Campus campus,
        String themeColor,
        String favoriteCafeteria
) {
}
