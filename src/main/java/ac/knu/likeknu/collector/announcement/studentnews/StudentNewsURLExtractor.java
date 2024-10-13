package ac.knu.likeknu.collector.announcement.studentnews;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public class StudentNewsURLExtractor {

    private static final RestClient restClient = RestClient.create();

    public static String extractRedirectURL(String originalURL) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("layout", "ylSMW362wYQN%2B2qo%2BkZuI7ECT9MbeWxhVFwsIyGN%2F0c%3D");

        ResponseEntity<String> response = restClient.post()
                .uri(originalURL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
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
