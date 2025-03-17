package com.woopaca.likeknu.logging.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    private LocalDateTime timestamp;

    private String method;

    private String path;

    private String queryString;

    private int duration;

    private int status;

    private String userAgent;

    private String requestBody;

    public LogEntry() {
    }

    @Builder
    public LogEntry(String deviceId, LocalDateTime timestamp, String method, String path, String queryString, int duration, int status, String userAgent, String requestBody) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.duration = duration;
        this.status = status;
        this.userAgent = userAgent;
        this.requestBody = requestBody;
    }
}
