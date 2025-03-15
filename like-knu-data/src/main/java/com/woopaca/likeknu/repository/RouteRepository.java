package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.RouteType;
import com.woopaca.likeknu.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, String> {

    List<Route> findByCampus(Campus campus);

    List<Route> findByCampusAndRouteType(Campus campus, RouteType routeType);
}
