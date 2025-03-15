package com.woopaca.likeknu.controller.dto.device.response;

import com.woopaca.likeknu.Tag;

public record SubscribeTagListResponse(String tag) {

    public static SubscribeTagListResponse of(Tag tag) {
        return new SubscribeTagListResponse(tag.getTagName());
    }
}
