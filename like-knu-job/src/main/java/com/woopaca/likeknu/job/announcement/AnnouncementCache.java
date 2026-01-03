package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import com.woopaca.likeknu.repository.AnnouncementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FIFO 캐시
 */
@Component
public class AnnouncementCache {

    private static final int CACHE_SIZE = 30;

    private final Map<Category, Set<AnnouncementMessage>> memory = new EnumMap<>(Category.class);

    private final AnnouncementRepository announcementRepository;

    public AnnouncementCache(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @PostConstruct
    void loadCache() {
        Arrays.stream(Category.values())
                .forEach(category -> {
                    LinkedHashSet<AnnouncementMessage> set = announcementRepository.findByCategory(category, Limit.of(CACHE_SIZE),
                                    Sort.by(Sort.Direction.DESC, "announcementDate"))
                            .stream()
                            .map(AnnouncementMessage::of)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    memory.put(category, set);
                });
    }

    public AnnouncementMessage cache(AnnouncementMessage announcementMessage) {
        Category category = announcementMessage.getCategory();
        Set<AnnouncementMessage> set = memory.get(category);

        if (set.size() >= CACHE_SIZE) {
            Iterator<AnnouncementMessage> iterator = set.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }

        set.add(announcementMessage);
        return announcementMessage;
    }

    public boolean isAbsent(AnnouncementMessage announcementMessage) {
        Category category = announcementMessage.getCategory();
        Set<AnnouncementMessage> set = memory.get(category);
        return !set.contains(announcementMessage);
    }
}
