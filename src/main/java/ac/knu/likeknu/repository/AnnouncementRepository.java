package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    /**
     * campus 리스트와 tag를 이용해 최상위 4개 공지 entity 가져오기
     *
     * @param campus
     * @return
     */
    List<Announcement> findTop4ByCampusInAndCategory(List<Campus> campus, Category category, Sort sort);

    Page<Announcement> findByCampusInAndCategory(Set<Campus> campuses, Category category, Pageable pageable);

    Slice<Announcement> findByCampusInAndAnnouncementTitleContains(Set<Campus> campus, String keyword, Pageable pageable);

    List<Announcement> findTop30ByCategoryOrderByAnnouncementDateDesc(Category category);

    Optional<Announcement> findByAnnouncementUrl(String url);
}
