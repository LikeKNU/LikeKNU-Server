package ac.knu.likeknu.collector.menu.domain;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record CafeteriaAttributes(List<CafeteriaAttribute> cafeteriaAttributes) {

    public static CafeteriaAttributes from(Document document) {
        Elements elements = document.select("#viewForm div table tbody tr");
        Elements date = document.select("#viewForm div table thead tr th");
        date.remove(0);

        List<CafeteriaAttribute> cafeteriaAttributes = elements.stream()
                .map(element -> CafeteriaAttribute.from(element, date))
                .filter(Objects::nonNull)
                .toList();
        return new CafeteriaAttributes(cafeteriaAttributes);
    }

    public Stream<CafeteriaAttribute> stream() {
        return cafeteriaAttributes.stream();
    }
}
