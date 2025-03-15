package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    Page<Notification> findByDevicesContainingAndNotificationDateGreaterThanEqual(Device device, LocalDateTime fromDate, Pageable pageable);
}
