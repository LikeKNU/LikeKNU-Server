package com.woopaca.likeknu.logging.service;

import com.woopaca.likeknu.logging.domain.UserLog;
import com.woopaca.likeknu.logging.domain.value.LogType;
import com.woopaca.likeknu.logging.repository.UserLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Transactional
@Service
public class LoggingService {

    private final UserLogRepository userLogRepository;

    public LoggingService(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

    @Async
    public void addLog(LogType logType, String deviceId, String... values) {
        UserLog userLog = UserLog.builder()
                .device(deviceId)
                .logType(logType)
                .value(String.join(".", values))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        userLogRepository.save(userLog);
    }
}
