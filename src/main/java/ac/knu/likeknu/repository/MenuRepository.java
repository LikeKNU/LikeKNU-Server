package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.constants.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    List<Menu> findByCafeteriaAndMenuDate(Cafeteria cafeteria, LocalDate menuDate);

    List<Menu> findByMenuDateAfterAndCafeteria(LocalDate menuDate, Cafeteria cafeteria);

    Optional<Menu> findByCafeteriaAndMenuDateAndMealType(Cafeteria cafeteria, LocalDate menuDate, MealType mealType);
}
