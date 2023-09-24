package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.value.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    Optional<Menu> findByMenuDateAndCafeteriaAndMealType(LocalDate menuDate, Cafeteria cafeteria, MealType mealType);
}
