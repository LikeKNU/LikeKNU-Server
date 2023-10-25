package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.base.PageDto;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import ac.knu.likeknu.repository.AnnouncementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class AnnouncementService {

    private static final int DEFAULT_ANNOUNCEMENT_PAGE_SIZE = 15;

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementListResponse> getAnnouncements(Campus campus, Category category, PageDto pageDto) {
        int requestPage = pageDto.getCurrentPage() - 1;
        PageRequest pageRequest = PageRequest.of(requestPage, DEFAULT_ANNOUNCEMENT_PAGE_SIZE,
                Sort.by(Direction.DESC, "announcementDate"));

        Page<Announcement> announcementsPage =
                announcementRepository.findByCampusInAndCategory(Set.of(campus, Campus.ALL), category, pageRequest);
        pageDto.updateTotalPages(announcementsPage.getTotalPages());
        pageDto.updateTotalElements(announcementsPage.getTotalElements());
        return announcementsPage.stream()
                .map(AnnouncementListResponse::of)
                .toList();
    }
}
