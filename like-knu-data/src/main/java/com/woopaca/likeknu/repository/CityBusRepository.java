package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.entity.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, String> {

    @EntityGraph(attributePaths = "arrivalTimes")
    List<CityBus> findByRoutesContaining(Route route);

    @EntityGraph(attributePaths = "arrivalTimes")
    List<CityBus> findByBusStop(String stopName);

    @EntityGraph(attributePaths = "arrivalTimes")
    List<CityBus> findByIsRealtime(boolean isRealtime);
}
