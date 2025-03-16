package com.woopaca.univclub.club.repository;

import com.woopaca.univclub.club.domain.Club;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClubJdbcRepository implements ClubRepository {

    private final JdbcClient jdbcClient;

    public ClubJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Club> findAll() {
        return jdbcClient.sql("SELECT * FROM club")
                .query(Club.class)
                .list();
    }

    @Override
    public Optional<Club> findById(Long id) {
        try {
            return jdbcClient.sql("SELECT * FROM club WHERE id = ?")
                    .params(id)
                    .query(Club.class)
                    .optional();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Club save(Club club) {
        if (club.getId() != null) {
            update(club);
            return club;
        }

        String sql = """
                INSERT INTO club (name, campus, category, tag, contact, instagram, recruitment_period, logo_image_url, introduction,
                                  introduction_image_url, membership_method, recruitment_url, youtube_url, homepage_url)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .params(club.getName(), club.getCampus().name(), club.getCategory().name(), club.getTag(), club.getContact(),
                        club.getInstagram(), club.getRecruitmentPeriod(), club.getLogoImageUrl(), club.getIntroduction(),
                        club.getIntroductionImageUrl(), club.getMembershipMethod(), club.getRecruitmentUrl(), club.getYoutubeUrl(),
                        club.getHomepageUrl())
                .update(keyHolder, "id");

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("지금 DB가 이상함");
        }
        return club.withGeneratedId(generatedId.longValue());
    }

    @Override
    public void update(Club club) {
        String sql = """
                UPDATE club
                SET name = ?, campus = ?, category = ?, tag = ?, instagram = ?, recruitment_period = ?,
                    introduction = ?, introduction_image_url = ?, membership_method = ?, recruitment_url = ?
                WHERE id = ?;
                """;
        jdbcClient.sql(sql)
                .params(club.getName(), club.getCampus().name(), club.getCategory().name(), club.getTag(),
                        club.getInstagram(), club.getRecruitmentPeriod(), club.getIntroduction(), club.getIntroductionImageUrl(),
                        club.getMembershipMethod(), club.getRecruitmentUrl(), club.getId())
                .update();
    }

    @Override
    public void updateLogoImage(String logoImageUrl, Long id) {
        String sql = "UPDATE club SET logo_image_url = ? WHERE id = ?";
        jdbcClient.sql(sql)
                .params(logoImageUrl, id)
                .update();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM club WHERE id = ?";
        jdbcClient.sql(sql)
                .params(id)
                .update();
    }
}
