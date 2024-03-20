package ac.knu.likeknu.logging.service;

import ac.knu.likeknu.logging.domain.UserLog;
import ac.knu.likeknu.logging.domain.value.LogType;
import ac.knu.likeknu.logging.repository.UserLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Transactional
@Service
public class LoggingService {

    private final UserLogRepository userLogRepository;

    public LoggingService(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

    @Async
    public void addLog(LogType logType, String... values) {
        UserLog userLog = UserLog.builder()
                .logType(logType)
                .value(String.join(".", values))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        userLogRepository.save(userLog);
    }
}
