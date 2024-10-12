package ac.knu.likeknu.collector.announcement.studentnews;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class StudentNewsURLExtractor {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static String extractRedirectURL(String originalURL) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("layout", "ylSMW362wYQN%2B2qo%2BkZuI7ECT9MbeWxhVFwsIyGN%2F0c%3D");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(originalURL, HttpMethod.POST, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        try {
            return Objects.requireNonNull(responseHeaders.getLocation())
                    .toString();
        } catch (NullPointerException exception) {
            return originalURL;
        }
    }
}
