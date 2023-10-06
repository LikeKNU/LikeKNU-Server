package ac.knu.likeknu.domain;

import ac.knu.likeknu.common.EntityGraphNames;
import ac.knu.likeknu.common.LocalTimeComparator;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = EntityGraphNames.BUS_ARRIVAL_TIMES,
                        attributeNodes = @NamedAttributeNode(value = "arrivalTimes")
                )
        }
)
@Table(name = "city_bus")
@Entity
public class CityBus {

    @Id
    private String id;

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
    @ElementCollection
    private List<LocalTime> arrivalTimes = new ArrayList<>();

    protected CityBus() {
    }

    @Builder
    protected CityBus(String id, String busNumber, String busName, String busColor, String busStop, Boolean isRealtime, List<LocalTime> arrivalTimes) {
        this.id = id;
        this.busNumber = busNumber;
        this.busName = busName;
        this.busColor = busColor;
        this.busStop = busStop;
        this.isRealtime = isRealtime;
        this.arrivalTimes = arrivalTimes;
    }

    public LocalTime getEarliestArrivalTime() {
        return this.arrivalTimes.stream()
                .filter(arrivalTime -> arrivalTime.isAfter(LocalTime.now().minusMinutes(1)))
                .filter(arrivalTime -> arrivalTime.isBefore(LocalTime.now().plusMinutes(60)))
                .min((time1, time2) -> new LocalTimeComparator().compare(time1, time2))
                .orElse(null);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CityBus cityBus = (CityBus) object;

        return Objects.equals(id, cityBus.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
