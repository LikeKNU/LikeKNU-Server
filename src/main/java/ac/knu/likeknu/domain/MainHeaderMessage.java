package ac.knu.likeknu.domain;

import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.exception.ErrorMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        if (message.length() > 16) {
            throw new BusinessException(ErrorMessage.INVALID_MAIN_MESSAGE_SIZE);
        }
        this.message = message;
    }

    public String formattedRegisteredDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return registeredAt.format(dateTimeFormatter);
    }
}
