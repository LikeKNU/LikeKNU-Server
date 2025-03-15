package com.woopaca.likeknu.controller.dto.suggestion.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SuggestionRequest(
        @NotBlank String deviceId,
        @NotBlank @Size(min = 1, max = 500) String content
) {
}
