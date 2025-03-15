package com.woopaca.likeknu.controller.dto.base;

public record PageResponseData<T>(T body, PageDto page) implements ResponseData<T> {
}
