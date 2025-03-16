package com.woopaca.univclub.club.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Campus {

    CHEONAN("천안캠"),
    SINGWAN("신관캠"),
    YESAN("예산캠");

    private final String name;

    Campus(String name) {
        this.name = name;
    }

    public static Campus ofName(@NotBlank String campus) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(campus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("캠퍼스 이름 이상한 거 넣지 마세요~"));
    }
}
