package com.woopaca.likeknu.collector.menu.constants;

import lombok.Getter;

@Getter
public enum Cafeteria implements CafeteriaProxy {

    SINGWAN_STUDENT_CAFETERIA(Campus.SINGWAN, "소담", "16862", 18),
    SINGWAN_EMPLOYEES_RESTAURANT(Campus.SINGWAN, "늘솜", "16863", 19),

    CHEONAN_STUDENT_CAFETERIA(Campus.CHEONAN, "학생식당", "16865", 20),
    CHEONAN_EMPLOYEES_RESTAURANT(Campus.CHEONAN, "직원식당", "16866", 21),
//    CHEONAN_DORMITORY_CAFETERIA(Campus.CHEONAN, "생활관식당", "16867", 22),

    YESAN_STUDENT_CAFETERIA(Campus.YESAN, "학생식당", "16869", 16),
    YESAN_EMPLOYEES_RESTAURANT(Campus.YESAN, "직원식당", "16870", 17);

    private final Campus campus;
    private final String name;
    private final String id;
    private final int number;

    Cafeteria(Campus campus, String name, String id, int number) {
        this.campus = campus;
        this.name = name;
        this.id = id;
        this.number = number;
    }
}
