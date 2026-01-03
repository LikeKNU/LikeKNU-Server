package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Category;
import com.woopaca.likeknu.entity.Announcement;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;
import com.woopaca.likeknu.repository.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AnnouncementCacheTest {

    private AnnouncementCache announcementCache;
    private AnnouncementRepository announcementRepositoryMock;

    @BeforeEach
    void setUp() {
        this.announcementRepositoryMock = Mockito.mock(AnnouncementRepository.class);
        this.announcementCache = new AnnouncementCache(this.announcementRepositoryMock);

        when(announcementRepositoryMock.findByCategory(any(), any(Limit.class), any(Sort.class)))
                .thenReturn(Collections.emptyList());

        announcementCache.loadCache();
    }

    @Test
    @DisplayName("새로운 공지사항을 캐시에 추가하면 absent가 false가 된다")
    void cache_newAnnouncement() {
        AnnouncementMessage announcement = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "새 공지");

        announcementCache.cache(announcement);

        assertThat(announcementCache.isAbsent(announcement)).isFalse();
    }

    @Test
    @DisplayName("캐시에 없는 공지사항은 absent가 true다")
    void isAbsent_notCached() {
        AnnouncementMessage announcement = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "없는 공지");

        assertThat(announcementCache.isAbsent(announcement)).isTrue();
    }

    @Test
    @DisplayName("FIFO: 30개 초과 시 가장 오래된 항목이 제거된다")
    void cache_fifo_eviction() {
        AnnouncementMessage first = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "첫번째");
        announcementCache.cache(first);

        for (int i = 0; i < 29; i++) {
            announcementCache.cache(AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "도서관" + i));
        }

        assertThat(announcementCache.isAbsent(first)).isFalse();
        AnnouncementMessage newOne = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "31번째");
        announcementCache.cache(newOne);

        assertThat(announcementCache.isAbsent(first)).isTrue();
        assertThat(announcementCache.isAbsent(newOne)).isFalse();
    }

    @Test
    @DisplayName("카테고리별로 독립적으로 관리된다")
    void cache_independentByCategory() {
        AnnouncementMessage library = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "도서관");
        AnnouncementMessage dormitory = AnnouncementFixtures.createAnnouncementMessage(Category.DORMITORY, "기숙사");

        announcementCache.cache(library);

        assertThat(announcementCache.isAbsent(library)).isFalse();
        assertThat(announcementCache.isAbsent(dormitory)).isTrue();
    }

    @Test
    @DisplayName("동일한 공지사항을 중복 캐싱해도 하나만 유지된다 (Set 특성)")
    void cache_duplicateAnnouncement() {
        AnnouncementMessage announcement = AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "중복 공지");

        announcementCache.cache(announcement);
        announcementCache.cache(announcement);
        announcementCache.cache(announcement);

        for (int i = 0; i < 29; i++) {
            announcementCache.cache(AnnouncementFixtures.createAnnouncementMessage(Category.LIBRARY, "도서관" + i));
        }

        assertThat(announcementCache.isAbsent(announcement)).isFalse();
    }

    @Test
    @DisplayName("loadCache: DB에서 최근 30개를 로드한다")
    void loadCache_loadsTop30FromDB() {
        List<Announcement> dbAnnouncements = IntStream.range(0, 30)
                .mapToObj(i -> AnnouncementFixtures.createAnnouncement("공지" + i, "https://example.com/" + i))
                .toList();

        when(announcementRepositoryMock.findByCategory(eq(Category.STUDENT_NEWS), any(Limit.class), any(Sort.class)))
                .thenReturn(dbAnnouncements);

        AnnouncementCache newCache = new AnnouncementCache(announcementRepositoryMock);
        newCache.loadCache();

        dbAnnouncements.forEach(announcement -> {
            AnnouncementMessage message = AnnouncementFixtures.createAnnouncementMessage(
                    announcement.getAnnouncementTitle(),
                    announcement.getAnnouncementUrl()
            );
            assertThat(newCache.isAbsent(message)).isFalse();
        });
    }
}
