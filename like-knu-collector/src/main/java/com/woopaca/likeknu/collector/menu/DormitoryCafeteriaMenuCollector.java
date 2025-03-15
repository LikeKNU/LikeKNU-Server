package com.woopaca.likeknu.collector.menu;

import com.woopaca.likeknu.collector.menu.constants.DormitoryCafeteria;
import com.woopaca.likeknu.collector.menu.domain.DormitoryCafeteriaAttributes;
import com.woopaca.likeknu.collector.menu.dto.Meal;
import com.woopaca.likeknu.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DormitoryCafeteriaMenuCollector implements MenuCollector {

    private final MenuProperties menuProperties;

    @Override
    public List<Meal> collectMenus() {
        return Arrays.stream(DormitoryCafeteria.values())
                .flatMap(dormitoryCafeteria -> collectMenu(dormitoryCafeteria).stream())
                .toList();
    }

    private List<Meal> collectMenu(DormitoryCafeteria dormitoryCafeteria) {
        String url = generateUrl(dormitoryCafeteria.getId());
        Connection connection = Jsoup.connect(url);

        try {
            Document document = connection.get();
            DormitoryCafeteriaAttributes cafeteriaAttributes = DormitoryCafeteriaAttributes.from(document);

            return cafeteriaAttributes.stream()
                    .map(cafeteriaAttribute -> Meal.of(dormitoryCafeteria, cafeteriaAttribute))
                    .filter(Meal::isNotEmpty)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl(String id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate previousSunday = DateTimeUtils.getPreviousOrSameDate(DayOfWeek.SUNDAY);
        LocalDate endDate = previousSunday.plusDays(13);
        String formattedStartDate = formatter.format(previousSunday);
        String formattedEndDate = formatter.format(endDate);

        return UriComponentsBuilder.fromUri(URI.create(menuProperties.getDormitoryCafeteriaPrefix() + id))
                .queryParam("currentWeekStart", formattedStartDate)
                .queryParam("currentWeekEnd", formattedEndDate)
                .build()
                .toUriString();
    }
}
