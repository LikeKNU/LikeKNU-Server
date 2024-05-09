package ac.knu.likeknu.domain;

import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Tag;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Table(name = "device")
@Entity
public class Device {

    @Id
    private String id;

    @Setter
    @Column(unique = true)
    private String fcmToken;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Campus campus;

    @Column(name = "notification", nullable = false)
    private boolean isTurnOnNotification;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    private String platform;

    private String modelName;

    private String osVersion;

    private String appVersion;

    private String themeColor;

    private String favoriteCafeteria;

    private LocalDateTime lastVisitedAt;

    @JoinTable(name = "device_bookmark",
            joinColumns = @JoinColumn(name = "device"),
            inverseJoinColumns = @JoinColumn(name = "announcement"))
    @ManyToMany
    private Set<Announcement> bookmarks = new HashSet<>();

    @JoinTable(name = "device_notification",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @ManyToMany
    private List<Notification> notifications = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name = "subscribe", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "tag")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Tag> subscribeTags = new ArrayList<>();

    protected Device() {
    }

    @Builder
    public Device(String id, String fcmToken, Campus campus, LocalDateTime registeredAt, String appVersion, String themeColor, String favoriteCafeteria) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.campus = campus;
        this.registeredAt = registeredAt;
        this.appVersion = appVersion;
        this.themeColor = themeColor;
        this.favoriteCafeteria = favoriteCafeteria;
    }

    public static Device of(DeviceRegistrationRequest request) {
        return Device.builder()
                .id(request.deviceId())
                .campus(Campus.SINGWAN)
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public void updateSubscribesTags(List<Tag> tags) {
        subscribeTags.clear();
        subscribeTags.addAll(tags);
    }

    public void updateNotification(boolean isTurnOnNotification) {
        this.isTurnOnNotification = isTurnOnNotification;
    }

    public void update(DeviceRegistrationRequest deviceRequest) {
        this.platform = deviceRequest.platform();
        this.campus = deviceRequest.campus();
        this.themeColor = deviceRequest.themeColor();
        this.favoriteCafeteria = deviceRequest.favoriteCafeteria();
        lastVisitedAt = LocalDateTime.now();

        if (deviceRequest.modelName() != null) {
            this.modelName = deviceRequest.modelName();
        }
        if (deviceRequest.osVersion() != null) {
            this.osVersion = deviceRequest.osVersion();
        }
        if (deviceRequest.appVersion() != null) {
            this.appVersion = deviceRequest.appVersion();
        }
    }
}
