package com.woopaca.likeknu.controller.dto.device.request;

import java.util.List;

public record SubscribeTagsUpdateRequest(String deviceId, List<TagName> tags) {
}
