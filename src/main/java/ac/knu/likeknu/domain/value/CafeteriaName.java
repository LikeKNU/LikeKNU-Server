package ac.knu.likeknu.domain.value;

import lombok.Getter;

@Getter
public enum CafeteriaName {

    STUDENT_CAFETERIA("학생식당", 1),
    DORMITORY("생활관식당", 2),
    EMPLOYEE_CAFETERIA("직원식당", 3),
    SODAM("소담", 4),
    NEULSOM("늘솜", 5),
    EUNHAENGSA_VISION("은행사/비전", 6),
    DREAM("드림", 7);

    private final String cafeteriaName;
    private final int sequence;

    CafeteriaName(String cafeteriaName, int sequence) {
        this.cafeteriaName = cafeteriaName;
        this.sequence = sequence;
    }

}
