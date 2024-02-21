package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Campus {

    ALL("공통", null),
    SINGWAN("신관캠", "notice"),
    CHEONAN("천안캠", "c-notice"),
    YESAN("예산캠", "y-notice");

    private final String campus;
    private final String dormitoryAnnouncementId;

    Campus(String campus, String dormitoryAnnouncementId) {
        this.campus = campus;
        this.dormitoryAnnouncementId = dormitoryAnnouncementId;
    }

    public static Campus of(String campusName) {
        return Arrays.stream(values())
                .filter(campus -> campus.getCampus().equals(campusName))
                .findAny()
                .orElseThrow();
    }
}
