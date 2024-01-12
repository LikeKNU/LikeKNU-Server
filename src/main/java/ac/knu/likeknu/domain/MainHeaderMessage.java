package ac.knu.likeknu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class MainHeaderMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16)
    private String message;

    private LocalDateTime registeredAt;

    protected MainHeaderMessage() {
        registeredAt = LocalDateTime.now();
    }

    public MainHeaderMessage(String message) {
        this();
        this.message = message;
    }
}
