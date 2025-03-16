package com.woopaca.univclub.club.repository;

import com.woopaca.univclub.club.domain.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepository {

    List<Club> findAll();

    Optional<Club> findById(Long id);

    Club save(Club club);

    void update(Club club);

    void updateLogoImage(String logoImageUrl, Long id);

    void deleteById(Long id);
}
