package com.woopaca.likeknu.logging.repository;

import com.woopaca.likeknu.logging.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
