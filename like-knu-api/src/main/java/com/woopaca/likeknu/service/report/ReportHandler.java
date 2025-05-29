package com.woopaca.likeknu.service.report;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.ReportType;
import com.woopaca.likeknu.external.slack.SlackChannels;
import com.woopaca.likeknu.external.slack.SlackClient;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class ReportHandler {

    private final SlackClient slackClient;

    protected ReportHandler(SlackClient slackClient) {
        this.slackClient = slackClient;
    }

    public void handle(Campus campus, Map<String, Object> data) {
        String message = this.generateMessage(campus, data);
        if (StringUtils.hasText(message)) {
            slackClient.sendMessageAsync(message, SlackChannels.REPORT);
        }
    }

    public abstract boolean isSupported(ReportType type);

    protected abstract String generateMessage(Campus campus, Map<String, Object> data);
}
