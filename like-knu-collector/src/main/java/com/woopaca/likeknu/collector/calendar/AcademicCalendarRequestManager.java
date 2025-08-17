package com.woopaca.likeknu.collector.calendar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class AcademicCalendarRequestManager {

    private final WebProperties webProperties;
    private final WebClient webClient;

    public AcademicCalendarRequestManager(WebProperties webProperties, WebClient webClient) {
        this.webProperties = webProperties;
        this.webClient = webClient;
    }

    public String fetchAcademicCalendarPage(int year, int month) {
        String uri = webProperties.getAcademicCalendar();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("year", String.valueOf(year));
        formData.add("month", String.valueOf(month));

        String responseBody = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if (responseBody == null) {
            log.error("[{}] Response body is null!", getClass().getName());
            throw new IllegalArgumentException(String.format("[%s] Response body is null!", getClass().getName()));
        }

        return responseBody;
    }
}
