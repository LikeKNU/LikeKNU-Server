package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, String> {

    @EntityGraph(attributePaths = "arrivalTimes")
    List<CityBus> findByRoutesContaining(Route route);
}
