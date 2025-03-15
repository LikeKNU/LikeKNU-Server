package com.woopaca.likeknu.fixture;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.entity.Announcement;

import java.time.LocalDate;
import java.time.Month;

public final class AnnouncementFixture {

    public static final String TITLE = "like-knu";
    public static final String URL = "https://fileisHere.com";
    public static final LocalDate DATE = LocalDate.of(2024, Month.MAY, 20);

    public static Announcement createAnnouncement() {
        return Announcement.builder()
                .announcementTitle(TITLE)
                .announcementUrl(URL)
                .announcementDate(DATE)
                .campus(Campus.SINGWAN)
                .category(Category.STUDENT_NEWS)
                .tag(Tag.ENROLMENT)
                .build();
    }

    public static Announcement createAnnouncementWithTitle(String title) {
        return Announcement.builder()
                .announcementTitle(title)
                .announcementUrl(URL)
                .announcementDate(DATE)
                .campus(Campus.SINGWAN)
                .category(Category.STUDENT_NEWS)
                .tag(Tag.ENROLMENT)
                .build();
    }
}
