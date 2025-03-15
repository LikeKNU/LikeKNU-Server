package com.woopaca.likeknu.collector.menu.domain;

import com.woopaca.likeknu.collector.menu.dto.MealType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record DormitoryCafeteriaAttributes(List<DormitoryCafeteriaAttribute> dormitoryCafeteriaAttributes) {

    public static DormitoryCafeteriaAttributes from(Document document) {
        Element tableElement = document.select(".table-board tbody")
                .first();
        assert tableElement != null;

        List<DormitoryCafeteriaAttribute> cafeteriaAttributes = Arrays.stream(MealType.values())
                .map(mealType -> DormitoryCafeteriaAttribute.of(mealType, tableElement))
                .toList();
        return new DormitoryCafeteriaAttributes(cafeteriaAttributes);
    }

    public Stream<DormitoryCafeteriaAttribute> stream() {
        return dormitoryCafeteriaAttributes.stream();
    }
}
