package com.woopaca.likeknu.collector.announcement.library;

import com.woopaca.likeknu.collector.announcement.AnnouncementProperties;
import com.woopaca.likeknu.collector.announcement.dto.Announcement;
import com.woopaca.likeknu.collector.menu.constants.Campus;
import com.woopaca.likeknu.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class LibraryAnnouncementParser {

    private final AnnouncementProperties announcementProperties;
    private final ObjectMapper objectMapper;

    public List<Announcement> parseLibraryAnnouncements(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = root.path("data");
            JsonNode listNode = dataNode.path("list");

            return StreamSupport.stream(listNode.spliterator(), false)
                    .map(LibraryAnnouncementElements::new)
                    .map(elements -> {
                        String title = elements.getTitle();
                        LocalDate date = elements.getDate();
                        String url = announcementProperties.getLibraryAnnouncementPrefixUrl() + elements.getId();
                        Campus campus = elements.getCampus();
                        return Announcement.ofLibrary(title, url, date, campus);
                    })
                    .toList();
        } catch (JsonProcessingException e) {
            throw new BusinessException("Failed to parse library announcement JSON body");
        }
    }
}
