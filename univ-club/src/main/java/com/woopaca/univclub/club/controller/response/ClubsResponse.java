package com.woopaca.univclub.club.controller.response;

import com.woopaca.univclub.club.domain.Club;
import lombok.Builder;

@Builder
public record ClubsResponse(Long id, String name, String campus, String category, String tag) {

    public static ClubsResponse from(Club club) {
        return ClubsResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .campus(club.getCampus().getName())
                .category(club.getCategory().getName())
                .tag(club.getTag())
                .build();
    }
}
