package com.woopaca.likeknu.service;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
public class SlackService {

    private static final String CHANNEL_NAME = "#서버-오류";

    @Value("${slack.webhook}")
    private String webhook;

    @Async
    public void sendMessage(String contents) {
        String message = contents + System.lineSeparator();
        Payload payload = buildPayload(message);

        try {
            WebhookResponse webhookResponse = Slack.getInstance()
                    .send(webhook, payload);
            if (webhookResponse.getCode() == HttpStatus.OK.value()) {
                log.info("Success send to slack!");
            }
            log.info(webhookResponse.getMessage());
        } catch (IOException e) {
            log.error("Unexpected Error! WebHook:" + webhook);
        }
    }

    private Payload buildPayload(String message) {
        return Payload.builder()
                .attachments(Collections.singletonList(Attachment.builder()
                        .channelName(CHANNEL_NAME)
                        .build()))
                .text(message)
                .build();
    }
}
