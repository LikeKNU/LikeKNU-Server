package ac.knu.likeknu.domain;

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
    private Long announcementId;

    @Column
    private String announcementTitle;

    @Column
    private String announcementUrl;

    @Column
    private LocalDate announcementDate;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Announcement(String announcementTitle, String announcementUrl, LocalDate announcementDate, Campus campus, Tag tag, Category category) {
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.tag = tag;
        this.category = category;
    }

}
