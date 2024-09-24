package ac.knu.likeknu.controller.dto.device.request;

import ac.knu.likeknu.domain.constants.Campus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeviceRegistrationRequest(String deviceId, String platform, String modelName, String osVersion,
                                        String appVersion, Campus campus, String themeColor, String favoriteCafeteria,
                                        String expoPushToken) {
}
