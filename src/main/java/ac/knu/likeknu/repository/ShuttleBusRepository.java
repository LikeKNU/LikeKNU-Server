package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Shuttle;
import ac.knu.likeknu.domain.ShuttleBus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShuttleBusRepository extends JpaRepository<ShuttleBus, String> {

    @EntityGraph(attributePaths = "shuttleTimes")
    List<ShuttleBus> findByShuttle(Shuttle shuttle);
}
