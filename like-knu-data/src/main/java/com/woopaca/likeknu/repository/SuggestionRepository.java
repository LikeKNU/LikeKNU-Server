package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
