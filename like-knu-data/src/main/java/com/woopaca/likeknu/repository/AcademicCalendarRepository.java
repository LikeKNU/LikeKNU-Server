package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, String> {

    @Query("""
            SELECT ac 
            FROM AcademicCalendar ac 
            WHERE (ac.startDate >= :start AND ac.startDate <= :end) 
               OR (ac.endDate >= :start AND ac.endDate <= :end) 
            ORDER BY ac.startDate 
            LIMIT 4
            """)
    List<AcademicCalendar> findBetweenDateLimit4(@Param("start") LocalDate start, @Param("end") LocalDate end);

    List<AcademicCalendar> findByStartDateBetween(LocalDate start, LocalDate end);

    List<AcademicCalendar> findByStartDateGreaterThanEqual(LocalDate startDate);
}
