package com.woopaca.likeknu.controller.dto.base;

import java.time.LocalDate;

public record MenuResponseData<T>(T body, LocalDate date) implements ResponseData<T> {
}
