package com.woopaca.likeknu.collector.menu;

import com.woopaca.likeknu.collector.menu.constants.Cafeteria;
import com.woopaca.likeknu.collector.menu.domain.CafeteriaAttributes;
import com.woopaca.likeknu.collector.menu.dto.Meal;
import com.woopaca.likeknu.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CafeteriaMenuCollector implements MenuCollector {

    private final MenuProperties menuProperties;
    private final RestClient restClient;

    @Override
    public List<Meal> collectMenus() {
        return Arrays.stream(Cafeteria.values())
                .flatMap(cafeteria -> collectMenu(cafeteria).stream())
                .collect(Collectors.toList());
    }

    private List<Meal> collectMenu(Cafeteria cafeteria) {
        try {
            CompletableFuture<List<Meal>> thisWeekMealsFuture = CompletableFuture
                    .supplyAsync(() -> fetchMealsForWeek(cafeteria, false));
            CompletableFuture<List<Meal>> nextWeekMealsFuture = CompletableFuture
                    .supplyAsync(() -> fetchMealsForWeek(cafeteria, true));

            return thisWeekMealsFuture.thenCombine(
                    nextWeekMealsFuture, (thisWeekMeals, nextWeekMeals) -> Stream.concat(thisWeekMeals.stream(), nextWeekMeals.stream())
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
                    .filter(cafeteriaAttribute -> cafeteriaAttribute.mealType() != null)
                    .map(cafeteriaAttribute -> Meal.of(cafeteria, cafeteriaAttribute))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl(Cafeteria cafeteria) {
        return menuProperties.getCafeteriaPrefix() + cafeteria.getId() + menuProperties.getCafeteriaSuffix();
    }

    private String generateNextWeekUrl(Cafeteria cafeteria) {
        LocalDate previousMonday = DateTimeUtils.getPreviousOrSameDate(DayOfWeek.MONDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("layout", "39BkRFnlItbhEGJ5CfPOEflabU8u4tWWDbOYcFOF2Xw%3D");
        formData.add("monday", formatter.format(previousMonday));
        formData.add("week", "next");

        ResponseEntity<String> response = restClient.post()
                .uri("https://www.kongju.ac.kr/diet/KNU/" + cafeteria.getNumber() + "/view.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        return Objects.requireNonNull(responseHeaders.getLocation())
                .toString();
    }
}
