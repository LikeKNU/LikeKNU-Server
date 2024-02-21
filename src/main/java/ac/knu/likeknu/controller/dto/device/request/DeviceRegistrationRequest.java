package ac.knu.likeknu.controller.dto.device.request;

import ac.knu.likeknu.domain.value.Campus;

public record DeviceRegistrationRequest(String deviceId, String userAgent, Campus campus) {
}
