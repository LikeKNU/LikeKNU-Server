package ac.knu.likeknu.logging.repository;

import ac.knu.likeknu.logging.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
