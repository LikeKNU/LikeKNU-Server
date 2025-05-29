package com.woopaca.likeknu.service;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.ReportType;
import com.woopaca.likeknu.service.report.ReportHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
public class ReportService {

    private final Collection<ReportHandler> reportHandlers;

    public ReportService(Collection<ReportHandler> reportHandlers) {
        this.reportHandlers = reportHandlers;
    }

    public void reportIssue(Campus campus, ReportType type, Map<String, Object> data) {
        reportHandlers.stream()
                .filter(handler -> handler.isSupported(type))
                .findAny()
                .ifPresentOrElse(handler -> handler.handle(campus, data),
                        () -> log.warn("지원하지 않는 ReportType: {}", type));
    }
}
