package ac.knu.likeknu.collector.announcement.library;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LibraryNoticeManager {

    private final AnnouncementProperties announcementProperties;
    private final RestClient restClient;

    public List<Announcement> fetchLibraryNotice() throws ParseException {
        String url = announcementProperties.getLibraryNotice();
        String response = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject responseObject = (JSONObject) jsonParser.parse(response);
        JSONObject jsonObject = (JSONObject) responseObject.get("data");
        JSONArray jsonArray = (JSONArray) jsonObject.get("list");

        return jsonArray.stream()
                .map(object -> {
                    JSONObject jsonObj = (JSONObject) object;

                    Campus campus = Campus.ALL;
                    Object bulletinTextHead = jsonObj.get("bulletinTextHead");
                    if (bulletinTextHead != null) {
                        String campusText = bulletinTextHead.toString();
                        campus = Arrays.stream(Campus.values())
                                .filter(it -> campusText.equals(it.getCampusLocation()))
                                .findAny()
                                .orElse(Campus.ALL);
                    }

                    String id = jsonObj.get("id").toString();
                    return Announcement.builder()
                            .title(jsonObj.get("title").toString())
                            .campus(campus)
                            .announcementDate(parseDate(jsonObj.get("dateCreated").toString()))
                            .announcementUrl(generateUrl(id))
                            .category(Category.LIBRARY)
                            .build();
                })
                .toList();
    }

    private LocalDate parseDate(String date) {
        date = date.split(" ")[0];
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String generateUrl(String id) {
        return announcementProperties.getLibraryFront() + id;
    }
}
