package ac.knu.likeknu.collector.menu;

import ac.knu.likeknu.collector.menu.constants.Cafeteria;
import ac.knu.likeknu.collector.menu.domain.CafeteriaAttributes;
import ac.knu.likeknu.collector.menu.dto.Meal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CafeteriaMenuCollector implements MenuCollector {

    private final MenuProperties menuProperties;

    @Override
    public List<Meal> collectMenus() {
        return Arrays.stream(Cafeteria.values())
                .flatMap(cafeteria -> collectMenu(cafeteria).stream())
                .collect(Collectors.toList());
    }

    private List<Meal> collectMenu(Cafeteria cafeteria) {
        String url = generateUrl(cafeteria.getId());
        Connection connection = Jsoup.connect(url);

        try {
            Document document = connection.get();
            CafeteriaAttributes cafeteriaAttributes = CafeteriaAttributes.from(document);

            return cafeteriaAttributes.stream()
                    .map(cafeteriaAttribute -> Meal.of(cafeteria, cafeteriaAttribute))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl(String id) {
        return menuProperties.getCafeteriaPrefix() + id + menuProperties.getCafeteriaPostfix();
    }
}
