package com.woopaca.likeknu.collector.announcement.library;

import com.woopaca.likeknu.collector.menu.constants.Campus;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LibraryAnnouncementElements {

    private static final String ID_ELEMENT_FIELD = "id";
    private static final String TITLE_ELEMENT_FIELD = "title";
    private static final String DATE_ELEMENT_FIELD = "dateCreated";
    private static final String CAMPUS_ELEMENT_FIELD = "bulletinTextHead";

    private final JsonNode elements;

    public LibraryAnnouncementElements(JsonNode elements) {
        this.elements = elements;
    }

    public Campus getCampus() {
        String bulletinTextHead = elements.path(CAMPUS_ELEMENT_FIELD)
                .asText(null);
        if (!StringUtils.hasText(bulletinTextHead)) {
            return Campus.ALL;
        }
        return Arrays.stream(Campus.values())
                .filter(it -> bulletinTextHead.equals(it.getCampusLocation()))
                .findAny()
                .orElse(Campus.ALL);
    }

    public String getId() {
        return elements.path(ID_ELEMENT_FIELD).asText();
    }

    public String getTitle() {
        return elements.path(TITLE_ELEMENT_FIELD).asText();
    }

    public LocalDate getDate() {
        String date = elements.path(DATE_ELEMENT_FIELD).asText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.split(" ")[0], dateTimeFormatter);
    }
}
