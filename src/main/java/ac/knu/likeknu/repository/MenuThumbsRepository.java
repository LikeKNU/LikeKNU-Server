package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.MenuThumbs;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuThumbsRepository extends JpaRepository<MenuThumbs, Long> {

    @EntityGraph(attributePaths = "device")
    List<MenuThumbs> findByMenu(Menu menu);

    Optional<MenuThumbs> findByMenuAndDevice(Menu menu, Device device);
}
