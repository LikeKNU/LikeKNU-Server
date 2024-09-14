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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Table(name = "city_bus")
@Entity
public class CityBus extends BaseEntity {

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
        LocalTime minimumTime = LocalTime.now().minusMinutes(1);
        LocalTime maximumTime = LocalTime.now().plusMinutes(60);
        return this.arrivalTimes.stream()
                .filter(minimumTime::isBefore)
                .filter(maximumTime::isAfter)
                .min(LocalTime::compareTo)
                .orElse(null);
    }

    public void updateArrivalTimes(Collection<LocalTime> arrivalTimes) {
        this.arrivalTimes.clear();
        this.arrivalTimes.addAll(arrivalTimes);
    }
}
