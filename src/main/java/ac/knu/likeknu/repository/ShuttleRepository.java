package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Shuttle;
import ac.knu.likeknu.domain.constants.Campus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShuttleRepository extends JpaRepository<Shuttle, String> {

    @EntityGraph(attributePaths = "shuttleBuses")
    List<Shuttle> findByCampusesContains(Campus campus);
}
