package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.constants.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, String> {

    List<Cafeteria> findByCampus(Campus campus);

    Optional<Cafeteria> findByCampusAndCafeteriaName(Campus campus, String cafeteria);
}
