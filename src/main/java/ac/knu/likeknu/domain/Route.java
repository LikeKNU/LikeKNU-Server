package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.RouteType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Table(name = "route")
@Entity
public class Route {

    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RouteType routeType;

    @Column(nullable = false)
    private String departureStop;

    @Column(nullable = false)
    private String arrivalStop;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Campus campus;

    @Column(nullable = false)
    private int sequence;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    @ManyToMany
    private List<CityBus> buses;

    protected Route() {
    }

    @Builder
    protected Route(String id, RouteType routeType, String departureStop, String arrivalStop, String origin, String destination, Campus campus) {
        this.id = id;
        this.routeType = routeType;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.origin = origin;
        this.destination = destination;
        this.campus = campus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Route route = (Route) object;

        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
