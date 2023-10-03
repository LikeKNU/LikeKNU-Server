package ac.knu.likeknu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "academic_calendar")
public class AcademicCalendar {

    @Id
    private String id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Builder
    public AcademicCalendar(String id, String contents, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
