package ac.knu.likeknu.domain;

import lombok.Getter;

@Getter
public enum Cafeteria {

    STUDENT_CAFETERIA("학생식당"),
    EMPLOYEE_CAFETERIA("직원식당"),
    EUNHAENGSA_VISION("은행사/비전"),
    DREAM("드림"),
    DORMITORY("생활관식당");

    private final String cafeteriaName;

    Cafeteria(String cafeteriaName) {
        this.cafeteriaName = cafeteriaName;
    }

}
