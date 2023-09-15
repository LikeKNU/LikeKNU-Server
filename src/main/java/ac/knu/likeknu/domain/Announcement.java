package ac.knu.likeknu.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    @Column
    private String announcementTitle;

    @Column
    private LocalDate announcementDate;

    @Builder
    public Announcement(Long announcementId, String announcementTitle, LocalDate announcementDate) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementDate = announcementDate;
    }
}
