package com.woopaca.likeknu.repository;

import com.woopaca.likeknu.entity.ReleaseVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReleaseVersionRepository extends JpaRepository<ReleaseVersion, Long> {

    @Query("""
            SELECT rv
            FROM ReleaseVersion rv
            ORDER BY rv.releaseDate DESC
            LIMIT 1
            """)
    ReleaseVersion findLatestVersion();
}
