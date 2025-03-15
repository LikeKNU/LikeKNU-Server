package com.woopaca.likeknu.collector.menu.constants;

import lombok.Getter;

@Getter
public enum Campus {

    ALL("공통", "공통", null),
    SINGWAN("신관", "공주", "notice"),
    CHEONAN("천안", "천안", "c-notice"),
    YESAN("예산", "예산", "y-notice");

    private final String campusName;
    private final String campusLocation;
    private final String dormitoryAnnouncementId;

    Campus(String campusName, String campusLocation, String dormitoryAnnouncementId) {
        this.campusName = campusName;
        this.campusLocation = campusLocation;
        this.dormitoryAnnouncementId = dormitoryAnnouncementId;
    }
}
