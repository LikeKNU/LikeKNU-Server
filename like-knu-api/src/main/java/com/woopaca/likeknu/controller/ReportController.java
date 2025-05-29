package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.controller.dto.report.ReportRequest;
import com.woopaca.likeknu.service.ReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/reports")
@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public String report(@RequestBody ReportRequest reportRequest) {
        reportService.reportIssue(reportRequest.campus(), reportRequest.type(), reportRequest.data());
        return "신고 접수";
    }
}
