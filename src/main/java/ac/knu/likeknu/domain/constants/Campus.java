package ac.knu.likeknu.domain.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Campus {

    ALL("공통", null),
    SINGWAN("신관캠", "notice"),
    CHEONAN("천안캠", "c-notice"),
    YESAN("예산캠", "y-notice");

    private final String name;
    private final String dormitoryAnnouncementId;

    Campus(String name, String dormitoryAnnouncementId) {
        this.name = name;
        this.dormitoryAnnouncementId = dormitoryAnnouncementId;
    }

    public static Campus of(String campusName) {
        return Arrays.stream(values())
                .filter(campus -> campus.getName().equals(campusName))
                .findAny()
                .orElseThrow();
    }
}
