package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.MainHeaderMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainHeaderMessageRepository extends JpaRepository<MainHeaderMessage, Long> {

    MainHeaderMessage findFirstByOrderByRegisteredAtDesc();
}
