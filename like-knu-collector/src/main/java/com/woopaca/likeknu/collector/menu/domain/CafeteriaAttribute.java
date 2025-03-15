package com.woopaca.likeknu.collector.menu.domain;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public record CafeteriaAttribute(Element mealType, List<MenuAttribute> menus) implements CafeteriaAttributeProxy {

    public static CafeteriaAttribute from(Element element, Elements dateElements) {
        try {
            Element mealTypeElement = element.select("th")
                    .first();
            Elements menuElements = element.select("td");
            List<MenuAttribute> menuAttributes = IntStream.range(0, dateElements.size())
                    .mapToObj(index -> MenuAttribute.of(menuElements.get(index), dateElements.get(index)))
                    .toList();
            return new CafeteriaAttribute(mealTypeElement, menuAttributes);
        } catch (Exception e) {
            log.warn("Failed to parse CafeteriaAttribute from element: {}", element, e);
            return null;
        }
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
