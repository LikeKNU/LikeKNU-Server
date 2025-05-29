package com.woopaca.likeknu.controller.dto.report;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.ReportType;

import java.util.Map;

public record ReportRequest(Campus campus, ReportType type, Map<String, Object> data) {
}
