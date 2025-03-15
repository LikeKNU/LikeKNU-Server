package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.job.announcement.ai.OpenAIFineTuning;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementTagAbstracter {

    private final OpenAIFineTuning openAIFineTuning;

    public AnnouncementTagAbstracter(OpenAIFineTuning openAIFineTuning) {
        this.openAIFineTuning = openAIFineTuning;
    }

    public Tag abstractTag(AnnouncementMessage announcementMessage) {
        if (announcementMessage.getCategory().equals(Category.STUDENT_NEWS)) {
            return openAIFineTuning.abstractTagOfAnnouncement(announcementMessage.getTitle());
        }

        return Tag.valueOf(announcementMessage.getCategory().name());
    }
}
