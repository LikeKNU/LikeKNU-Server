package ac.knu.likeknu.repository;

import ac.knu.likeknu.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicesRepository extends JpaRepository<Device, String> {
}
