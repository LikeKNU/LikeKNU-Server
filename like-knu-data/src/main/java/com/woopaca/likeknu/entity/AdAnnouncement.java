package com.woopaca.likeknu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class AdAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "is_ad_exposed", nullable = false)
    private boolean isAdExposed;

    protected AdAnnouncement() {
    }

    @Builder
    public AdAnnouncement(String title, String subTitle, String contents, boolean isAdExposed) {
        this.title = title;
        this.subTitle = subTitle;
        this.contents = contents;
        this.isAdExposed = isAdExposed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        AdAnnouncement that = (AdAnnouncement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
