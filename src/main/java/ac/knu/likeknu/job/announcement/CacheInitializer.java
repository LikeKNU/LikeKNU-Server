package ac.knu.likeknu.job.announcement;

import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
import ac.knu.likeknu.repository.AnnouncementRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class CacheInitializer implements InitializingBean {

    private final AnnouncementCache announcementCache;
    private final AnnouncementRepository announcementRepository;

    public CacheInitializer(AnnouncementCache announcementCache, AnnouncementRepository announcementRepository) {
        this.announcementCache = announcementCache;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public void afterPropertiesSet() {
        Arrays.stream(Category.values())
                .map(announcementRepository::findTop30ByCategoryOrderByCollectedAtDesc)
                .flatMap(Collection::stream)
                .map(AnnouncementMessage::of)
                .forEach(announcementCache::cache);
    }
}
