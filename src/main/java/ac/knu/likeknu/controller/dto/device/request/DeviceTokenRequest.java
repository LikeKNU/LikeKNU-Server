package ac.knu.likeknu.controller.dto.device.request;

import lombok.Getter;

@Getter
public record DeviceTokenRequest(String deviceId, String token) {
}
