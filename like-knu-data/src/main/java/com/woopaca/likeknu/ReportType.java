package com.woopaca.likeknu;

public enum ReportType {

    CAFETERIA_DATA_ISSUE("식당 메뉴 정보 이슈");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }
}
