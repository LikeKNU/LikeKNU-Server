package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "device")
@Entity
public class Device {

    @Id
    private String id;

    @Column(unique = true)
    private String fcmToken;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Campus campus;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @JoinTable(name = "device_notification",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @ManyToMany
    private List<Notification> notifications = new ArrayList<>();

    protected Device() {
    }

    @Builder
    public Device(String id, String fcmToken, Campus campus, LocalDateTime registeredAt) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.campus = campus;
        this.registeredAt = registeredAt;
    }
}
