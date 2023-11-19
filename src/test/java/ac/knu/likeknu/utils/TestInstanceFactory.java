package ac.knu.likeknu.utils;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.CityBus;
import ac.knu.likeknu.domain.Route;
import ac.knu.likeknu.domain.value.RouteType;
import ac.knu.likeknu.domain.value.Tag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class TestInstanceFactory {

    public static Route createRoute(String departureStop, String arrivalStop) {
        return Route.builder()
                .id(UUID.randomUUID().toString())
                .departureStop(departureStop)
                .arrivalStop(arrivalStop)
                .routeType(RouteType.OUTGOING)
                .build();
    }

    public static Route createRoute(String departureStop, String arrivalStop, String origin, String destination) {
        return Route.builder()
                .id(UUID.randomUUID().toString())
                .departureStop(departureStop)
                .arrivalStop(arrivalStop)
                .origin(origin)
                .destination(destination)
                .build();
    }

    public static CityBus createCityBus(String busNumber) {
        LocalTime currentTime = LocalTime.now();
        return CityBus.builder()
                .id(UUID.randomUUID().toString())
                .busNumber(busNumber)
                .arrivalTimes(List.of(currentTime.minusMinutes(10), currentTime, currentTime.plusMinutes(10)))
                .build();
    }

    public static Announcement createAnnouncement(String title, String url, Tag tag) {
        return Announcement.builder()
                .announcementTitle(title)
                .announcementDate(LocalDate.now())
                .announcementUrl(url)
                .tag(tag)
                .build();
    }
}
