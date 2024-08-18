package ac.knu.likeknu.collector.menu.domain;

import ac.knu.likeknu.collector.menu.dto.MealType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record DormitoryCafeteriaAttribute(MealType mealType,
                                          List<MenuAttribute> menus) implements CafeteriaAttributeProxy {

    public static DormitoryCafeteriaAttribute of(MealType mealType, Element tableElement) {
        Elements dateElements = tableElement.select("td[data-mqtitle=date]");
        Elements menuElements = tableElement.select("td[data-mqtitle=" + mealType.name()
                .toLowerCase() + "]");
        List<MenuAttribute> menuAttributes = IntStream.range(0, dateElements.size())
                .mapToObj(index -> MenuAttribute.of(menuElements.get(index), dateElements.get(index)))
                .toList();
        return new DormitoryCafeteriaAttribute(mealType, menuAttributes);
    }

    @Override
    public String getMealType() {
        return mealType.getKorean();
    }

    @Override
    public Stream<MenuAttribute> menuStream() {
        return menus.stream();
    }
}
