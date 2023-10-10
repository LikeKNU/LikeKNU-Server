package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CafeteriaName {

    STUDENT_CAFETERIA("학생식당", 1),
    EUNHAENGSA_VISION("은행사/비전", 2),
    DREAM("드림", 3),
    DORMITORY("생활관식당", 4),
    EMPLOYEE_CAFETERIA("직원식당", 5);

    private final String cafeteriaName;
    private final int sequence;

    CafeteriaName(String cafeteriaName, int sequence) {
        this.cafeteriaName = cafeteriaName;
        this.sequence = sequence;
    }

}
