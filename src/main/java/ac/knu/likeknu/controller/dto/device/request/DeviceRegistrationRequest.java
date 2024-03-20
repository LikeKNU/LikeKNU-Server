package ac.knu.likeknu.controller.dto.device.request;

public record DeviceRegistrationRequest(
        String deviceId, String userAgent, String campus, String themeColor, String favoriteCafeteria
) {
}
