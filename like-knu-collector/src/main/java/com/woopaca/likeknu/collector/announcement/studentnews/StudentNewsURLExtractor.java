package com.woopaca.likeknu.collector.announcement.studentnews;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class StudentNewsURLExtractor {

    private final RestClient restClient;

    public StudentNewsURLExtractor(RestClient restClient) {
        this.restClient = restClient;
    }

    public String extractRedirectURL(String originalURL) {
        URI uri = UriComponentsBuilder.fromUriString(originalURL)
                .queryParam("layout", "unknown")
                .build()
                .toUri();

        ResponseEntity<String> response = restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        try {
            return Objects.requireNonNull(responseHeaders.getLocation())
                    .toString();
        } catch (NullPointerException exception) {
            return originalURL;
        }
    }
}
