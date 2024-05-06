package ac.knu.likeknu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "suggestion")
@Entity
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @JoinColumn(name = "device")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    protected Suggestion() {
    }

    @Builder
    public Suggestion(String content, LocalDateTime createdAt, Device device) {
        this.content = content;
        this.createdAt = createdAt;
        this.device = device;
    }

    public static Suggestion of(Device device, String content) {
        return new Suggestion(content, LocalDateTime.now(), device);
    }
}
