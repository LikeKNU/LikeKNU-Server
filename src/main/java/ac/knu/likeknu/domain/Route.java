package ac.knu.likeknu.domain;

import ac.knu.likeknu.common.EntityGraphNames;
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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = EntityGraphNames.ROUTE_BUSES_ARRIVAL_TIMES,
                        attributeNodes = {
                                @NamedAttributeNode(value = "buses", subgraph = "CityBus.arrivalTimes")
                        },
                        subgraphs = {
                                @NamedSubgraph(name = "CityBus.arrivalTimes", attributeNodes = @NamedAttributeNode(value = "arrivalTimes"))
                        }
                )
        }
)
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

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    @ManyToMany
    private List<CityBus> buses;

    protected Route() {
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
