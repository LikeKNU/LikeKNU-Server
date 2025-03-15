/*
package ac.knu.likeknu.job.citybus.init;

import com.woopaca.likeknu.entity.CityBus;
import com.woopaca.likeknu.repository.CityBusRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TimeTableInitializer {

    private final RestClient restClient;
    private final CityBusRepository cityBusRepository;

    public TimeTableInitializer(RestClient restClient, CityBusRepository cityBusRepository) {
        this.restClient = restClient;
        this.cityBusRepository = cityBusRepository;
    }

    @Transactional
    public void initializeCheonanCityBusTime(Long cityBusId) {
        CityBus cityBus = cityBusRepository.findById(cityBusId)
                .orElseThrow(IllegalArgumentException::new);

        String uri = UriComponentsBuilder.fromUriString("https://its.cheonan.go.kr/bis/showBusTimeTable.do")
                .queryParam("routeName", cityBus.getBusNumber())
                .queryParam("routeDirection", 1)
                .queryParam("relayAreacode")
                .queryParam("routeExplain")
                .queryParam("stName")
                .queryParam("edName")
                .queryParam("fstTime")
                .queryParam("lstTime")
                .queryParam("routeDLength")
                .queryParam("avgTime")
                .toUriString();
        String pageSource = restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
        Document document = Jsoup.parse(pageSource);
        Element timeTable = document.getElementsByClass("timeTable").get(0);
        Elements timeElements = timeTable.getElementsByTag("dd");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (Element timeElement : timeElements) {
            String text = timeElement.text();
            if (text.contains("(")) {
                text = text.split("\\(")[0];
            }
            LocalTime time = LocalTime.parse(text, dateTimeFormatter).plusMinutes(13);
        }
    }

    @Transactional
    public void initializeYesanCityBusTime() throws IOException {
        String filePath = "src/main/resources/static/bus.csv";
        FileReader fileReader = new FileReader(filePath);
        CSVParser csvRecords = CSVFormat.Builder.create().setDelimiter(',').build().parse(fileReader);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (CSVRecord csvRecord : csvRecords) {
            String busNumber = csvRecord.get(0);
            String arrivalTime = csvRecord.get(1);

            CityBus cityBus = cityBusRepository.findByBusNumberAndBusStop(busNumber, "예산역")
                    .orElseGet(() -> {
                        CityBus newCityBus = CityBus.builder().busNumber(busNumber).busName(busNumber).busStop("예산역").build();
                        return cityBusRepository.save(newCityBus);
                    });

            LocalTime time = LocalTime.parse(arrivalTime, dateTimeFormatter);
            cityBus.addArrivalTime(time);
        }
    }
}
*/
