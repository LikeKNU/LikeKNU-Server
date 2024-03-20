package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.ShuttleType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Table(name = "shuttle")
@Entity
public class Shuttle {

    @Id
    private String id;

    private String origin;

    private String destination;

    @Enumerated(EnumType.STRING)
    private ShuttleType shuttleType;

    private String note;

    private int sequence;

    @OneToMany(mappedBy = "shuttle")
    private List<ShuttleBus> shuttleBuses = new ArrayList<>();

    @CollectionTable(name = "shuttle_campus", joinColumns = @JoinColumn(name = "shuttle_id"))
    @Column(name = "campus")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Campus> campuses = new ArrayList<>();

    protected Shuttle() {
    }

    @Builder
    public Shuttle(String origin, String destination, ShuttleType shuttleType, String note) {
        this.origin = origin;
        this.destination = destination;
        this.shuttleType = shuttleType;
        this.note = note;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Shuttle shuttle = (Shuttle) object;

        return Objects.equals(id, shuttle.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
