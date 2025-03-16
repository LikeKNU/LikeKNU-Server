package com.woopaca.univclub.club.repository;

import com.woopaca.univclub.club.domain.Club;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@SuppressWarnings("unused")
public interface ClubJpaRepository extends JpaRepository<Club, Long>, ClubRepository {

    @Modifying
    @Query("""
            UPDATE Club c
            SET c.logoImageUrl = :logoImageUrl
            WHERE c.id = :id
            """)
    void updateLogoImage(@Param("logoImageUrl") String logoImageUrl, @Param("id") Long id);

    @Modifying
    @Query("""
            UPDATE Club c
            SET c.name = :#{#club.name},
                c.category = :#{#club.category},
                c.tag = :#{#club.tag},
                c.campus = :#{#club.campus},
                c.recruitmentPeriod = :#{#club.recruitmentPeriod},
                c.introduction = :#{#club.introduction},
                c.membershipMethod = :#{#club.membershipMethod},
                c.instagram = :#{#club.instagram},
                c.recruitmentUrl = :#{#club.recruitmentUrl},
                c.contact = :#{#club.contact},
                c.youtubeUrl = :#{#club.youtubeUrl},
                c.homepageUrl = :#{#club.homepageUrl}
            WHERE c.id = :#{#club.id}
            """)
    @Override
    void update(Club club);
}
