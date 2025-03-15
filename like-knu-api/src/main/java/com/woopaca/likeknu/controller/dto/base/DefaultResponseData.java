package com.woopaca.likeknu.controller.dto.base;

public record DefaultResponseData<T>(T body) implements ResponseData<T> {
}
