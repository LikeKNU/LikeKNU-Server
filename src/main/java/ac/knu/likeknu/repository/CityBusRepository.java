package ac.knu.likeknu.repository;

import ac.knu.likeknu.common.EntityGraphNames;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, String> {

    @EntityGraph(value = EntityGraphNames.BUS_ARRIVAL_TIMES)
    List<CityBus> findByRoutesContaining(Route route);
}
