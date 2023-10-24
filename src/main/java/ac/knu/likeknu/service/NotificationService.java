package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.base.PageDto;
import ac.knu.likeknu.controller.dto.notification.NotificationListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class NotificationService {

    public List<NotificationListResponse> getNotificationList(String deviceId, Duration duration, PageDto pageDto) {
        return null;
    }
}
