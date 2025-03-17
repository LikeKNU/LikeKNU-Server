package com.woopaca.likeknu.logging.repository;

import com.woopaca.likeknu.logging.domain.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
