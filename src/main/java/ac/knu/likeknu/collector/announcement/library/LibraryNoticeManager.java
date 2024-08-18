package ac.knu.likeknu.collector.announcement.library;

import ac.knu.likeknu.collector.announcement.AnnouncementProperties;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.menu.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryNoticeManager {

    private final AnnouncementProperties announcementProperties;

    public List<Announcement> fetchLibraryNotice() throws ParseException {
        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> responseEntity = rt.getForEntity(announcementProperties.getLibraryNotice(), String.class);
        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        Object obj = jsonParser.parse(responseEntity.getBody());
        JSONObject jsonObject = (JSONObject) ((JSONObject) obj).get("data");
        JSONArray jsonArray = (JSONArray) jsonObject.get("list");

        List<Announcement> list = new ArrayList<>();

        for (Object object : jsonArray) {
            JSONObject jsonObj = (JSONObject) object;

            Campus campus = Campus.ALL;
            Object bulletinTextHead = jsonObj.get("bulletinTextHead");
            if (bulletinTextHead != null) {
                String cp = bulletinTextHead.toString();
                for (Campus c : Campus.values())
                    campus = cp.equals(c.getCampusLocation()) ? c : campus;
            }

            String id = jsonObj.get("id").toString();

            Announcement announcement = Announcement.builder()
                    .title(jsonObj.get("title").toString())
                    .campus(campus)
                    .announcementDate(parseDate(jsonObj.get("dateCreated").toString()))
                    .announcementUrl(generateUrl(id))
                    .category(Category.LIBRARY)
                    .build();
            list.add(announcement);
        }

        return list;
    }

    private LocalDate parseDate(String date) {
        date = date.split(" ")[0];
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String generateUrl(String id) {
        return announcementProperties.getLibraryFront() + id + announcementProperties.getLibraryBack();
    }
}
