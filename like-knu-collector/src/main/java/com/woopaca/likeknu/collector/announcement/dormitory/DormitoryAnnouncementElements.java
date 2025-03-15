package com.woopaca.likeknu.collector.announcement.dormitory;

import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DormitoryAnnouncementElements {

    private static final int TITLE_COLUMN_INDEX = 2;
    private static final int DATE_COLUMN_INDEX = 3;

    private final Elements elements;

    public DormitoryAnnouncementElements(Elements elements) {
        this.elements = elements;
    }

    public String getTitle() {
        return elements.get(TITLE_COLUMN_INDEX).text();
    }

    public LocalDate getDate() {
        String date = elements.get(DATE_COLUMN_INDEX).text();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public String getPath() {
        return elements.get(TITLE_COLUMN_INDEX).select("a").first()
                .attr("href");
    }
}
