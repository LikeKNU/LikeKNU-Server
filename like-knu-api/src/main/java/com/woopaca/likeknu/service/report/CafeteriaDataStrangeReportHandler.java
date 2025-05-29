package com.woopaca.likeknu.service.report;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.ReportType;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.external.slack.SlackClient;
import com.woopaca.likeknu.repository.CafeteriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Component
public class CafeteriaDataStrangeReportHandler extends ReportHandler {

    private static final String MESSAGE_TEMPLATE = """
            🍴*식당 메뉴 정보가 이상해요! 확인이 필요합니다.*
            
            ● 디바이스 ID: `{deviceId}`
            ● 캠퍼스: `{campus}`
            ● 식당 이름: `{cafeteriaName}`
            """;

    private final CafeteriaRepository cafeteriaRepository;

    protected CafeteriaDataStrangeReportHandler(SlackClient slackClient, CafeteriaRepository cafeteriaRepository) {
        super(slackClient);
        this.cafeteriaRepository = cafeteriaRepository;
    }

    @Override
    public boolean isSupported(ReportType type) {
        return type == ReportType.CAFETERIA_DATA_ISSUE;
    }

    @Override
    protected String generateMessage(Campus campus, Map<String, Object> data) {
        if (CollectionUtils.isEmpty(data)) {
            log.warn("data가 비어있습니다.");
            return null;
        }

        String cafeteriaId = (String) data.get("cafeteriaId");
        if (cafeteriaId == null) {
            log.warn("cafeteriaId가 null입니다.");
            cafeteriaId = "none";
        }
        String deviceId = (String) data.get("deviceId");
        if (deviceId == null) {
            log.warn("deviceId가 null입니다.");
            deviceId = "none";
        }

        String cafeteriaName = cafeteriaRepository.findById(cafeteriaId)
                .map(Cafeteria::getCafeteriaName)
                .orElse("");
        return MESSAGE_TEMPLATE
                .replace("{deviceId}", deviceId)
                .replace("{campus}", campus.getName())
                .replace("{cafeteriaName}", cafeteriaName);
    }
}
