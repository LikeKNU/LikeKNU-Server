package com.woopaca.univclub.club.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {

    ACADEMIC("학술"),
    SPORTS("체육"),
    ART("예술"),
    HOBBY("취미"),
    RELIGION("종교"),
    VOLUNTEER("봉사"),
    PERFORMANCE("공연");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Category ofName(String category) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("카테고리 이름 이상한 거 넣지 마세요~"));
    }
}
