package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.job.announcement.ai.OpenAIFineTuning;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;

public class OpenAIAnnouncementTagAbstracter implements AnnouncementTagAbstracter {

    private final OpenAIFineTuning openAIFineTuning;

    public OpenAIAnnouncementTagAbstracter(OpenAIFineTuning openAIFineTuning) {
        this.openAIFineTuning = openAIFineTuning;
    }

    @Override
    public Tag abstractTag(AnnouncementMessage announcementMessage) {
        if (announcementMessage.getCategory().equals(Category.STUDENT_NEWS)) {
            return openAIFineTuning.abstractTagOfAnnouncement(announcementMessage.getTitle());
        }

        return Tag.valueOf(announcementMessage.getCategory().name());
    }
}
