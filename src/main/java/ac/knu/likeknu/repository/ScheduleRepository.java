package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<AcademicCalendar, String> {
}
