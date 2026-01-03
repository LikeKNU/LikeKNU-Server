package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;

import java.time.LocalDate;

public class AnnouncementFixtures {

    public static Announcement createAnnouncement(String title, String url) {
        return Announcement.builder()
                .announcementTitle(title)
                .announcementUrl(url)
                .announcementDate(LocalDate.now())
                .campus(Campus.SINGWAN)
                .category(Category.STUDENT_NEWS)
                .tag(Tag.STUDENT_NEWS)
                .build();
    }

    public static AnnouncementMessage createAnnouncementMessage(String title, String url) {
        return AnnouncementMessage.builder()
                .title(title)
                .announcementUrl(url)
                .announcementDate(LocalDate.now())
                .campus(Campus.SINGWAN)
                .category(Category.STUDENT_NEWS)
                .build();
    }

    public static AnnouncementMessage createAnnouncementMessage(Category category, String title) {
        return AnnouncementMessage.builder()
                .title(title)
                .announcementUrl("https://example.com/" + title)
                .announcementDate(LocalDate.now())
                .campus(Campus.SINGWAN)
                .category(category)
                .build();
    }
}
