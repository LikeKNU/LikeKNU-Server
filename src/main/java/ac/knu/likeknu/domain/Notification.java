package ac.knu.likeknu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Table(name = "notification")
@Entity
public class Notification {

    @Id
    private String id;

    private String notificationTitle;

    private String notificationBody;

    private LocalDate notificationDate;

    private Boolean read;

    protected Notification() {
    }

    @Builder
    public Notification(String notificationTitle, String notificationBody, LocalDate notificationDate, Boolean read) {
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
        this.read = read;
    }
}
