package ac.knu.likeknu.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Table(name = "city_bus")
@Entity
public class CityBus extends BaseEntity {

    public static final Duration MINIMUM_OFFSET_MINUTES = Duration.ofMinutes(1);
    public static final Duration MAXIMUM_OFFSET_MINUTES = Duration.ofMinutes(30);

    @Column(nullable = false)
    private String busNumber;

    private String busName;

    private String busColor;

    @Column(nullable = false)
    private String busStop;

    private Boolean isRealtime;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    @ManyToMany
    private List<Route> routes;

    @CollectionTable(name = "bus_time", joinColumns = @JoinColumn(name = "bus_id"))
    @Column(name = "arrival_time")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<LocalTime> arrivalTimes = new ArrayList<>();

    protected CityBus() {
    }

    @Builder
    protected CityBus(String busNumber, String busName, String busColor, String busStop, Boolean isRealtime, List<LocalTime> arrivalTimes) {
        this.busNumber = busNumber;
        this.busName = busName;
        this.busColor = busColor;
        this.busStop = busStop;
        this.isRealtime = isRealtime;
        this.arrivalTimes = arrivalTimes;
    }

    public LocalTime getEarliestArrivalTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return this.arrivalTimes.stream()
                .map(arrivalTime -> LocalDateTime.of(currentDateTime.toLocalDate(), arrivalTime))
                .filter(currentDateTime.minus(MINIMUM_OFFSET_MINUTES)::isBefore)
                .filter(currentDateTime.plus(MAXIMUM_OFFSET_MINUTES)::isAfter)
                .min(LocalDateTime::compareTo)
                .map(LocalDateTime::toLocalTime)
                .orElse(null);
    }

    public List<LocalTime> getArrivalTimesWithinRange() {
        LocalTime currentTime = LocalTime.now();
        return arrivalTimes.stream()
                .filter(currentTime.minus(MINIMUM_OFFSET_MINUTES)::isBefore)
                .filter(currentTime.plus(MAXIMUM_OFFSET_MINUTES)::isAfter)
                .toList();
    }

    public void updateArrivalTimes(Collection<LocalTime> arrivalTimes) {
        this.arrivalTimes.clear();
        this.arrivalTimes.addAll(arrivalTimes);
    }

    public void clearArrivalTime() {
        arrivalTimes.clear();
    }
}
