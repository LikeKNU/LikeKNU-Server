package com.woopaca.likeknu.collector.calendar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class AcademicCalendarRequestManager {

    private final WebProperties webProperties;
    private final RestClient restClient;

    public AcademicCalendarRequestManager(WebProperties webProperties, RestClient restClient) {
        this.webProperties = webProperties;
        this.restClient = restClient;
    }

    public String fetchAcademicCalendarPage(int year, int month) {
        String uri = webProperties.getAcademicCalendar();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("year", String.valueOf(year));
        formData.add("month", String.valueOf(month));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String responseBody = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(headers -> headers.addAll(httpHeaders))
                .body(formData)
                .retrieve()
                .body(String.class);
        if (responseBody == null) {
            log.error("[{}] Response body is null!", getClass().getName());
            throw new IllegalArgumentException(String.format("[%s] Response body is null!", getClass().getName()));
        }

        return responseBody;
    }
}
