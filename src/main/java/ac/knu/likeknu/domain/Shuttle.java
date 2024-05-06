package ac.knu.likeknu.domain;

import ac.knu.likeknu.converter.OperatingDaysConverter;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.ShuttleType;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.utils.DateTimeUtils;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
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

    @Convert(converter = OperatingDaysConverter.class)
    @Column(name = "operating_days", columnDefinition = "SET")
    private EnumSet<DayOfWeek> operatingDays;

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
    public Shuttle(String origin, String destination, ShuttleType shuttleType, String note, EnumSet<DayOfWeek> operatingDays) {
        this.origin = origin;
        this.destination = destination;
        this.shuttleType = shuttleType;
        this.note = note;
        this.operatingDays = operatingDays;
    }

    public LocalDateTime getNextDepartureDateTime() {
        if (isAvailableToday()) {
            LocalTime todayNextDepartureTime = shuttleBuses.stream()
                    .filter(ShuttleBus::isAvailableToday)
                    .map(ShuttleBus::getDepartureTime)
                    .sorted()
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("There are no shuttle buses available."));
            return LocalDateTime.of(LocalDate.now(), todayNextDepartureTime);
        }

        LocalTime nextDateEarliestDepartureTime = shuttleBuses.stream()
                .map(ShuttleBus::getDepartureTime)
                .sorted()
                .findFirst()
                .orElseThrow(() -> new BusinessException("There are no shuttle buses available."));
        LocalDate earliestNextAvailableDate = DateTimeUtils.getEarliestNextAvailableDate(operatingDays);
        return LocalDateTime.of(earliestNextAvailableDate, nextDateEarliestDepartureTime);
    }

    private boolean isAvailableToday() {
        // TODO Judgment including public holidays
        DayOfWeek currentDayOfWeek = LocalDate.now()
                .getDayOfWeek();
        if (!operatingDays.contains(currentDayOfWeek)) {
            return false;
        }

        return shuttleBuses.stream()
                .anyMatch(ShuttleBus::isAvailableToday);
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
