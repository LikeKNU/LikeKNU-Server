package ac.knu.likeknu.controller.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationListResponse {

    private final String notificationId;
    private final String notificationTitle;
    private final String notificationBody;
    private final String notificationDate;
    private final String notificationUrl;
    private final boolean isRead;

    @Builder
    public NotificationListResponse(String notificationId, String notificationTitle, String notificationBody, String notificationDate, String notificationUrl, boolean isRead) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
        this.notificationUrl = notificationUrl;
        this.isRead = isRead;
    }
}
