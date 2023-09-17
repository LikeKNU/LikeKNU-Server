package ac.knu.likeknu.domain;

import lombok.Getter;

@Getter
public enum Campus {

    ALL("공통", null),
    SINGWAN("신관", "notice"),
    CHEONAN("천안", "y-notice"),
    YESAN("예산", "c-notice");

    private final String campus;
    private final String dormitoryAnnouncementId;

    Campus(String campus, String dormitoryAnnouncementId) {
        this.campus = campus;
        this.dormitoryAnnouncementId = dormitoryAnnouncementId;
    }

    public static Campus of(String campus) {
        if(campus == null)
            throw new IllegalArgumentException();

        for(Campus c : Campus.values()) {
            if(c.campus.equals(campus))
                return c;
        }

        throw new IllegalArgumentException("일치하는 캠퍼스가 없습니다.");
    }
}
