package ac.knu.likeknu.common;

import java.time.LocalTime;
import java.util.Comparator;

public class LocalTimeComparator implements Comparator<LocalTime> {

    private static final LocalTime BASE_TIME = LocalTime.of(5, 0, 0);

    @Override
    public int compare(LocalTime time1, LocalTime time2) {
        boolean time1AfterBase = time1.isAfter(BASE_TIME);
        boolean time2AfterBase = time2.isAfter(BASE_TIME);

        if (time1AfterBase && !time2AfterBase) {
            return -1;
        } else if (!time1AfterBase && time2AfterBase) {
            return 1;
        } else {
            return time1.compareTo(time2);
        }
    }
}
