package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.entity.Shuttle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShuttleRepository extends JpaRepository<Shuttle, String> {

    @EntityGraph(attributePaths = "shuttleBuses")
    List<Shuttle> findByCampusesContains(Campus campus);
}
