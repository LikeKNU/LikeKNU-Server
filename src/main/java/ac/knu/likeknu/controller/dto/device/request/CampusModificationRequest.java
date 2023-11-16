package ac.knu.likeknu.controller.dto.device.request;

import lombok.Getter;

@Getter
public record CampusModificationRequest(String deviceId, String campus) {
}
