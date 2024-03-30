package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.constants.ThumbsType;

public record MenuThumbsRequest(String deviceId, ThumbsType thumbsType) {
}
