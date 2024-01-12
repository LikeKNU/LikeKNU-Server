package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.MainHeaderMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainHeaderMessageRepository extends JpaRepository<MainHeaderMessage, Long> {

    MainHeaderMessage findFirstByOrderByRegisteredAtDesc();
}
