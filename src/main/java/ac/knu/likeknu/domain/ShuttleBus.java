package ac.knu.likeknu.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "shuttle_bus")
@Entity
public class ShuttleBus {

    @Id
    private String id;

    private String shuttleName;

    @JoinColumn(name = "shuttle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Shuttle shuttle;

    @OneToMany(mappedBy = "shuttleBus", cascade = CascadeType.ALL)
    private List<ShuttleTime> shuttleTimes = new ArrayList<>();

    protected ShuttleBus() {
    }

    @Builder
    public ShuttleBus(String shuttleName, Shuttle shuttle) {
        this.shuttleName = shuttleName;
        this.shuttle = shuttle;
    }
}
