package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
