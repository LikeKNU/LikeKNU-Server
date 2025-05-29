package com.woopaca.likeknu.external.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class SlackClient {

    private final SlackProperties slackProperties;
    private final Slack slack;

    public SlackClient(SlackProperties slackProperties) {
        this.slackProperties = slackProperties;
        this.slack = Slack.getInstance();
    }

    public void sendMessage(String message, String channel) {
        String webhookUrl = slackProperties.getWebhook();
        Attachment attachment = Attachment.builder()
                .channelName(channel)
                .build();
        Payload payload = Payload.builder()
                .attachments(Collections.singletonList(attachment))
                .blocks(List.of(SectionBlock.builder()
                        .text(MarkdownTextObject.builder()
                                .text(message)
                                .build())
                        .build()))
                .build();

        try {
            WebhookResponse response = slack.send(webhookUrl, payload);
            if (response.getCode() != 200) {
                log.warn("Slack 메시지 전송 실패. code: {}, message: {}", response.getCode(), response.getMessage());
            }
            log.info("Slack 메시지 전송 성공. message: {}, channel: {}", message, channel);
        } catch (IOException e) {
            log.warn("Slack 메시지 전송 중 오류 발생. message: {}", e.getMessage());
        }
    }

    @Async
    public void sendMessageAsync(String message, String channel) {
        sendMessage(message, channel);
    }
}
