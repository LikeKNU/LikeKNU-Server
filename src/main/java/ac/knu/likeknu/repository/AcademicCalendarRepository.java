package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, String> {

    List<AcademicCalendar> findTop4ByStartDateBetweenOrderByStartDateAsc(LocalDate start, LocalDate end);

    List<AcademicCalendar> findByStartDateBetweenOrEndDateBetween(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);
}
