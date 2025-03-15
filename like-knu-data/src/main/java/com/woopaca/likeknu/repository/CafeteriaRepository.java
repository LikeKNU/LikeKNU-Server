package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, String> {

    List<Cafeteria> findByCampus(Campus campus);

    Optional<Cafeteria> findByCampusAndCafeteriaName(Campus campus, String cafeteria);
}
