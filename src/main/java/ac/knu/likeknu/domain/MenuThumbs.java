package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.ThumbsType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Table(name = "menu_thumbs")
@Entity
public class MenuThumbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ThumbsType thumbsType;

    private Timestamp thumbsAt;

    @JoinColumn(name = "device")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    @JoinColumn(name = "menu")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    protected MenuThumbs() {
    }

    @Builder
    public MenuThumbs(ThumbsType thumbsType, Timestamp thumbsAt, Device device, Menu menu) {
        this.thumbsType = thumbsType;
        this.thumbsAt = thumbsAt;
        this.device = device;
        this.menu = menu;
    }
}
