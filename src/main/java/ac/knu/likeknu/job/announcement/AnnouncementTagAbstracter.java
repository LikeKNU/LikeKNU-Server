package ac.knu.likeknu.job.announcement;

import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.domain.constants.Tag;
import ac.knu.likeknu.job.announcement.ai.OpenAIFineTuning;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
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
