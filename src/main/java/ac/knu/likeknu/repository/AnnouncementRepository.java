package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Campus;
import ac.knu.likeknu.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    /**
     * campus 리스트와 tag를 이용해 최상위 4개 공지 entity 가져오기
     * @param campus
     * @return
     */
    Optional<List<Announcement>> findTop4ByCampusInAndCategoryOrderByAnnouncementDateDesc(List<Campus> campus, Category category);

}
