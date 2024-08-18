package ac.knu.likeknu.collector.menu.domain;

import java.util.stream.Stream;

public interface CafeteriaAttributeProxy {

    String getMealType();

    Stream<MenuAttribute> menuStream();
}
