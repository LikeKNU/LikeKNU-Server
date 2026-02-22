package com.woopaca.univclub.club.controller.response;

import com.woopaca.univclub.club.domain.Club;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubDetailsResponse(Long id, String name, String campus, String category, String tag, String contact,
                                  String instagram, String recruitmentPeriod, String logoImageUrl, String introduction,
                                  String introductionImageUrl, String membershipMethod, String recruitmentUrl,
                                  String youtubeUrl, String homepageUrl, LocalDateTime createdAt,
                                  LocalDateTime updatedAt) {

    public static ClubDetailsResponse from(Club club) {
        return ClubDetailsResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .campus(club.getCampus().getName())
                .category(club.getCategory().getName())
                .tag(club.getTag())
                .contact(club.getContact())
                .instagram(club.getInstagram())
                .recruitmentPeriod(club.getRecruitmentPeriod())
                .logoImageUrl(club.getLogoImageUrl())
                .introduction(club.getIntroduction())
                .introductionImageUrl(club.getIntroductionImageUrl())
                .membershipMethod(club.getMembershipMethod())
                .recruitmentUrl(club.getRecruitmentUrl())
                .youtubeUrl(club.getYoutubeUrl())
                .homepageUrl(club.getHomepageUrl())
                .createdAt(club.getCreatedAt())
                .updatedAt(club.getUpdatedAt())
                .build();
    }
}
