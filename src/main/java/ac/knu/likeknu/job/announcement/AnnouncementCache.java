package ac.knu.likeknu.job.announcement;

import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.job.announcement.dto.AnnouncementMessage;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FIFO 캐시
 */
@Component
public class AnnouncementCache {

    private static final int CACHE_SIZE = 30;

    private final Map<Category, BlockingQueue<AnnouncementMessage>> memory = new ConcurrentHashMap<>();
    private final Map<Category, Set<AnnouncementMessage>> memorySupporter = new ConcurrentHashMap<>();

    public AnnouncementCache() {
        Arrays.stream(Category.values())
                .forEach(category -> {
                    memory.put(category, new ArrayBlockingQueue<>(CACHE_SIZE));
                    memorySupporter.put(category, Collections.synchronizedSet(new HashSet<>(CACHE_SIZE)));
                });
    }

    public AnnouncementMessage cache(AnnouncementMessage announcementMessage) {
        Category category = announcementMessage.getCategory();
        memory.computeIfPresent(category, (key, queue) -> {
            Set<AnnouncementMessage> set = memorySupporter.get(category);
            if (queue.remainingCapacity() == 0) {
                AnnouncementMessage removalAnnouncement = queue.poll();
                set.remove(removalAnnouncement);
            }
            queue.add(announcementMessage);
            set.add(announcementMessage);
            return queue;
        });
        return announcementMessage;
    }

    public boolean isAbsent(AnnouncementMessage announcementMessage) {
        Category category = announcementMessage.getCategory();
        Set<AnnouncementMessage> set = memorySupporter.get(category);
        return !set.contains(announcementMessage);
    }
}
