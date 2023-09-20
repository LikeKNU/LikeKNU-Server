package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Campus;
import ac.knu.likeknu.domain.MealType;
import ac.knu.likeknu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    Optional<List<Menu>> findByDateAndCampusAndMealType(LocalDate Date, Campus campus, MealType mealType);

}
