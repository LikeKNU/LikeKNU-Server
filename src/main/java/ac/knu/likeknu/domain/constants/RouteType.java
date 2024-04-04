package ac.knu.likeknu.domain.constants;

import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.exception.ErrorMessage;

import java.time.LocalTime;
import java.util.Arrays;

public enum RouteType {

    OUTGOING, INCOMING;

    private static final LocalTime ROUTE_TYPE_CHANGE_TIME = LocalTime.of(12, 0);

    public static RouteType of(String type) {
        return Arrays.stream(values())
                .filter(routeType -> isSame(type, routeType))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorMessage.INVALID_MAIN_MESSAGE_SIZE));
    }

    private static boolean isSame(String type, RouteType routeType) {
        String name = routeType.name();
        String lowerCase = name.toLowerCase();
        return lowerCase.equals(type);
    }

    public static RouteType determineRouteType(LocalTime time) {
        if (time.isBefore(ROUTE_TYPE_CHANGE_TIME)) {
            return RouteType.INCOMING;
        }
        return RouteType.OUTGOING;
    }
}
