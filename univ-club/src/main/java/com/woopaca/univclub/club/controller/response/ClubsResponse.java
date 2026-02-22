package com.woopaca.univclub.club.controller.response;

import com.woopaca.univclub.club.domain.Club;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubsResponse(Long id, String name, String campus, String category, String tag, LocalDateTime createdAt,
                            LocalDateTime updatedAt) {

    public static ClubsResponse from(Club club) {
        return ClubsResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .campus(club.getCampus().getName())
                .category(club.getCategory().getName())
                .tag(club.getTag())
                .createdAt(club.getCreatedAt())
                .updatedAt(club.getUpdatedAt())
                .build();
    }
}
