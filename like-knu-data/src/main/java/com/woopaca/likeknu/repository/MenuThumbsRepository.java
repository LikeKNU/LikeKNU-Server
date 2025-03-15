package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.entity.Menu;
import com.woopaca.likeknu.entity.MenuThumbs;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuThumbsRepository extends JpaRepository<MenuThumbs, Long> {

    @EntityGraph(attributePaths = "device")
    List<MenuThumbs> findByMenu(Menu menu);

    Optional<MenuThumbs> findByMenuAndDevice(Menu menu, Device device);
}
