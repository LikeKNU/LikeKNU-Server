package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.response.PageDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    public List<AnnouncementListResponse> getAnnouncements(Campus campus, Category category, PageDto pageDto) {
        return null;
    }
}
