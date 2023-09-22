package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Category;
import ac.knu.likeknu.domain.value.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Announcement {

    @Id
    private String id;

    @Column
    private String announcementTitle;

    @Column
    private String announcementUrl;

    @Column
    private LocalDate announcementDate;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    protected Announcement() {
    }

    @Builder
    public Announcement(String announcementTitle, String announcementUrl, LocalDate announcementDate, Campus campus, Category category, Tag tag) {
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.category = category;
        this.tag = tag;
    }

}
