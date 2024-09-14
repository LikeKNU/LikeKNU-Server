package ac.knu.likeknu.collector.menu;

import ac.knu.likeknu.collector.menu.constants.Cafeteria;
import ac.knu.likeknu.collector.menu.domain.CafeteriaAttributes;
import ac.knu.likeknu.collector.menu.dto.Meal;
import ac.knu.likeknu.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try {
            CompletableFuture<List<Meal>> thisWeekMealsFuture = CompletableFuture.supplyAsync(() -> fetchMealsForWeek(cafeteria, false));
            CompletableFuture<List<Meal>> nextWeekMealsFuture = CompletableFuture.supplyAsync(() -> fetchMealsForWeek(cafeteria, true));

            return thisWeekMealsFuture.thenCombine(
                    nextWeekMealsFuture, (thisWeekMeals, nextWeekMeals) ->
                            Stream.concat(thisWeekMeals.stream(), nextWeekMeals.stream())
                                    .toList()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Meal> fetchMealsForWeek(Cafeteria cafeteria, boolean isNextWeek) {
        try {
            String url = isNextWeek ? generateNextWeekUrl(cafeteria) : generateUrl(cafeteria);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            CafeteriaAttributes cafeteriaAttributes = CafeteriaAttributes.from(document);
            return cafeteriaAttributes.stream()
                    .map(cafeteriaAttribute -> Meal.of(cafeteria, cafeteriaAttribute))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl(Cafeteria cafeteria) {
        return menuProperties.getCafeteriaPrefix() + cafeteria.getId() + menuProperties.getCafeteriaPostfix();
    }

    private String generateNextWeekUrl(Cafeteria cafeteria) {
        LocalDate previousMonday = DateTimeUtils.getPreviousOrSameDate(DayOfWeek.MONDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String url = String.format("/diet/kongju/%d/view.do?monday=%s&week=next&", cafeteria.getNumber(), formatter.format(previousMonday));
        String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);
        String combined = "fnct1|@@|" + encodedUrl;
        String base64Encoded = Base64.getEncoder().encodeToString(combined.getBytes(StandardCharsets.UTF_8));

        return UriComponentsBuilder.fromUriString(generateUrl(cafeteria))
                .queryParam("enc", base64Encoded)
                .build()
                .toUriString();
    }
}
