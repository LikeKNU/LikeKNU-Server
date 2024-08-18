package ac.knu.likeknu.collector.menu.constants;

import lombok.Getter;

@Getter
public enum Cafeteria implements CafeteriaProxy {

    SINGWAN_STUDENT_CAFETERIA(Campus.SINGWAN, "소담", "13155"),
    SINGWAN_EMPLOYEES_RESTAURANT(Campus.SINGWAN, "늘솜", "13156"),

    CHEONAN_STUDENT_CAFETERIA(Campus.CHEONAN, "학생식당", "13157"),
    CHEONAN_EMPLOYEES_RESTAURANT(Campus.CHEONAN, "직원식당", "13158"),
    CHEONAN_DORMITORY_CAFETERIA(Campus.CHEONAN, "생활관식당", "13163"),

    YESAN_STUDENT_CAFETERIA(Campus.YESAN, "학생식당", "13159"),
    YESAN_EMPLOYEES_RESTAURANT(Campus.YESAN, "직원식당", "13160");

    private final Campus campus;
    private final String name;
    private final String id;

    Cafeteria(Campus campus, String name, String id) {
        this.campus = campus;
        this.name = name;
        this.id = id;
    }
}
