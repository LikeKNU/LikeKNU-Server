package ac.knu.likeknu.service;

import ac.knu.likeknu.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

    private final AnnouncementRepository announcementRepository;

}
