package com.woopaca.likeknu.entity;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.Domain;
import com.woopaca.likeknu.RouteType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Table(name = "route")
@Entity
public class Route extends BaseEntity {

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
        super(Domain.ROUTE);
    }

    @Builder
    protected Route(RouteType routeType, String departureStop, String arrivalStop, String origin, String destination, Campus campus) {
        this.routeType = routeType;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.origin = origin;
        this.destination = destination;
        this.campus = campus;
    }
}
