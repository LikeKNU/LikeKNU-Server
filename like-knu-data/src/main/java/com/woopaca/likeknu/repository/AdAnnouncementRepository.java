package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.AdAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdAnnouncementRepository extends JpaRepository<AdAnnouncement, Long> {

    List<AdAnnouncement> findByIsAdExposedIsTrue();
}
