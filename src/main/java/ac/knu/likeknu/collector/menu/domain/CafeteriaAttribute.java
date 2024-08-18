package ac.knu.likeknu.collector.menu.domain;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record CafeteriaAttribute(Element mealType, List<MenuAttribute> menus) implements CafeteriaAttributeProxy {

    public static CafeteriaAttribute from(Element element, Elements dateElements) {
        Element mealTypeElement = element.select("th")
                .first();
        Elements menuElements = element.select("td");
        List<MenuAttribute> menuAttributes = IntStream.range(0, dateElements.size())
                .mapToObj(index -> MenuAttribute.of(menuElements.get(index), dateElements.get(index)))
                .toList();
        return new CafeteriaAttribute(mealTypeElement, menuAttributes);
    }

    @Override
    public String getMealType() {
        return mealType.text();
    }

    @Override
    public Stream<MenuAttribute> menuStream() {
        return menus.stream();
    }
}
