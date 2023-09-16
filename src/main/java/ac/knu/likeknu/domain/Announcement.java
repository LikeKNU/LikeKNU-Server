package ac.knu.likeknu.domain;

import ac.knu.likeknu.controller.dto.response.MainAnnouncementsResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String announcementId;

    @Column
    private String announcementTitle;

    @Column String announcementUrl;

    @Column
    private LocalDate announcementDate;

    private String campus;

    private String tag;

    @Builder
    public Announcement(String announcementId, String announcementTitle, String announcementUrl, LocalDate announcementDate, String campus, String tag) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.tag = tag;
    }

}
