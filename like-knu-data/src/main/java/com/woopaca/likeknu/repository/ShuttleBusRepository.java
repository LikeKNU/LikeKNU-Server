package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.Shuttle;
import com.woopaca.likeknu.entity.ShuttleBus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShuttleBusRepository extends JpaRepository<ShuttleBus, String> {

    @EntityGraph(attributePaths = "shuttleTimes")
    List<ShuttleBus> findByShuttle(Shuttle shuttle);
}
