package ac.knu.likeknu.logging.domain;

import ac.knu.likeknu.logging.domain.value.LogType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Table(name = "user_log")
@Entity
public class UserLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private LogType logType;

    @Column
    private String value;

    @Column
    private Timestamp timestamp;

    protected UserLog() {
    }

    @Builder
    public UserLog(LogType logType, String value, Timestamp timestamp) {
        this.logType = logType;
        this.value = value;
        this.timestamp = timestamp;
    }
}
