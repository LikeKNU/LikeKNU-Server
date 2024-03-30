package ac.knu.likeknu.domain.constants;

import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.exception.ErrorMessage;

import java.util.Arrays;

public enum RouteType {

    OUTGOING, INCOMING;

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
}
