package com.woopaca.likeknu.logging;

import com.woopaca.likeknu.logging.domain.LogEntry;

import java.time.LocalDateTime;

public record ApiRequestLog(LocalDateTime timestamp, String method, String uri, String queryString, String duration,
                            int status, String userAgent, String deviceId, String requestBody) {

    public LogEntry toEntity() {
        return LogEntry.builder()
                .deviceId(deviceId)
                .timestamp(timestamp)
                .method(method)
                .path(uri)
                .queryString(queryString)
                .duration(Integer.parseInt(duration))
                .status(status)
                .userAgent(userAgent)
                .requestBody(requestBody)
                .build();
    }
}
